def get_options(opts){

  def options = [:]

  def option_names = [
    'docker_registry',
    'docker_image_name_prefix',
    'docker_build_args'
  ]

  def option_defaults = [
    'docker_registry': 'registrycompany.azure.com',
    'docker_image_name_prefix': '',
    'docker_build_args': ''
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
  // options = get_options(module_opts)
  // echo "option docker_registry = ${options['docker_registry']}"
  // echo "option docker_image_name_prefix = ${options['docker_image_name_prefix']}"
}

return this;
