try {

  def modules = []
  def config_object

  node {
    // Clean-up workspace
    deleteDir()
    // Code checkout
    stage("Init Modules") {
      checkout changelog: false,
               poll: false,
               scm: [
                 $class: 'GitSCM',
                 branches: [[name: '*/master']],
                 doGenerateSubmoduleConfigurations: false,
                 userRemoteConfigs: [
                   [ credentialsId: 'git',
                     url: 'git@github.com:edsonmarquezani/jenkins.git'
                   ]
                 ]
               ]
    }

    list = env.JOB_NAME.split('/')
    repo_name = list[list.size()-2]

    // Reads Pipeline meta-config
    config_object = readYaml file: "pipelines/${repo_name}.yml"

    // Load modules to be use in the following stages
    for (i=0; i < config_object.stages.size(); i++) {
      module_name = config_object.stages[i].module
      module_path = "modules/${config_object.version}/${module_name}.groovy"
      module = load module_path
      modules[i] = module
    }
  }

  node(config_object.node_type) {

    stage("Code Checkout") {
      // Checkout source code
      checkout scm
    }

    // Iterates main 'stages' list and launchs every stage defined invoking its
    // defined module
    for (i=0; i < config_object.stages.size(); i++) {

      // Getting stage
      stage_name = config_object.stages[i].name

      module_opts = [:]
      if (config_object.stages[i].options != null) {
        module_opts = config_object.stages[i].options
      }
      
      // Declares the stage
      stage(stage_name) {
        modules[i].main()
      }
    }
  }

} catch(exec) {
  err = caughtError
  currentBuild.result = "FAILURE"
}
