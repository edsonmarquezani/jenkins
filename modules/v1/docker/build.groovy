def get_options(opts){

  def options = [:]

  def option_names = [
    'registry',
    'image_name_prefix',
    'image_name_suffix_branch'
  ]

  def option_defaults = [
    'registry': env.DOCKER_REGISTRY_ACCOUNT,
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
  echo "option registry = ${options['registry']}"
  echo "option image_name_prefix = ${options['image_name_prefix']}"
  echo "option image_name_suffix_branch = ${options['image_name_suffix_branch']}"
}

return this;
