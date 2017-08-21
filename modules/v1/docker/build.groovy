def get_options(opts){

  def options = [:]

  def option_names = [
    'registry',
    'image_name_prefix',
    'image_name_suffix_branch'
  ]

  def option_defaults = [
    'registry': env.AZBR_REG_HOST,
    'image_name_prefix': '',
    'image_name_suffix_branch': true
  ]

  // Getting module options and setting defaults
  def i=0
  for (i=0; i < option_names.size(); i++) {
    name = option_names[i]
    value = (opts!=null && opts[name] != null) ? opts[name] : option_defaults[name]
    options.put (name, value)
  }

  return options
}

def main() {
  options = get_options(module_opts)

  // Build tag name
  tag = "${options['registry']}/${options['image_name_prefix']}"
  if(options['image_name_suffix_branch']) {
    tag = "${tag}-${env.BRANCH_NAME}"
  }
  tag = "${tag}:${env.BUILD_NUMBER}"

  docker_build(tag)
}

def docker_build(tag) {
  withEnv(["IMAGE_TAG=${tag}"]) {
    sh '''
      if [ -f Dockerfile ]; then
        docker login -u ${AZBR_REG_USR} -p ${AZBR_REG_PWD} -e ${EMAIL_INFRA_DIGITAL} ${AZBR_REG_HOST}
        docker -H ${SWARM_API_HOST}:${SWARM_API_PORT} build --no-cache -t ${IMAGE_TAG} .
      else
        echo "Project does not contain a Dockerfile. Skipping Build."
      fi
    '''
  }
}

return this;
