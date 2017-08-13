try {

  groovy_dir = "/tmp/${env.BUILD_TAG}"

  // Code checkout
  stage("Init") {
    checkout changelog: false,
             poll: false,
             scm: [
               $class: 'GitSCM',
               branches: [[name: '*/master']],
               doGenerateSubmoduleConfigurations: false,
               extensions: [[
                 $class: 'RelativeTargetDirectory',
                 targetDir: groovyDir
               ]],
               userRemoteConfigs: [
                 [ credentialsId: 'git',
                   url: 'git@github.com:edsonmarquezani/jenkins.git'
                 ]
               ]
             ]
    checkout scm
  }

  // Reads Pipeline meta-config
  config_object = readYaml file: 'pipeline.yml'

  node(config_object.node_type) {
    // Iterates main 'stages' list and launchs every stage defined invoking its
    // defined module
    def i=0
    for (i=0; i < config_object.stages.size(); i++) {

      // Getting stage
      stage_name = configObject.stages[i].name
      module_name = configObject.stages[i].module
      submodule_name = configObject.stages[i].submodule
      module_opts = configObject.stages[i].options

      // module path includes the API version declared in the config, along
      // with module/submodule
      module_path = "${configObject.version}/modules/${module_name}/${submodule_name}.groovy"

      // Declares the stage
      stage(stage_name) {
        evaluate(readFile("${groovy_dir}/module_path"))
      }
    }
  }
} catch(exec) {
  err = caughtError
  currentBuild.result = "FAILURE"
}
