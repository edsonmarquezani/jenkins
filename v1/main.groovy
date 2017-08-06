try {

  node {

    // Code checkout
    stage("Code Checkout") {
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

    // Reads Pipeline meta-config
    configObject = readYaml file: 'pipeline.yml'

    // Iterates main 'stages' list and launchs every stage defined invoking its
    // defined module
    def i=0
    for (i=0; i < configObject.stages.size(); i++) {

      // Getting stage
      stage_name = configObject.stages[i].name
      module_opts = configObject.stages[i].options
      // module path includes the API version declared in the config, along
      // with module/submodule
      module_path = "${configObject.version}/modules/${configObject.stages[i].module}.groovy"

      // Declares the stage
      stage(stage_name) {
         evaluate(readFile(module_path))
      }
    }
  }
} catch(exec) {
  err = caughtError
  currentBuild.result = "FAILURE"
}
