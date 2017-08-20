def get_options(opts){

  def options = [:]

  def option_names = [
    'cluster_name_per_env',
    'namespace',
    'replicaset'
  ]

  def option_defaults = [
    'cluster_name_per_env': null,
    'namespace': null,
    'replicaset': null
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
  echo "option replicaset = ${options['replicaset']}"
  echo "option namespace = ${options['namespace']}"
}

return this;
