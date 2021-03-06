def get_options(opts){

  def options = [:]

  def option_names = [
    'build_command',
    'build_args_per_branch'
  ]

  def option_defaults = [
    'build_command:': './build.sh',
    'build_args_per_branch': null
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
  echo "option build_command = ${options['build_command']}"
}

return this;
