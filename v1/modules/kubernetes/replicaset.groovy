def get_options(opts){

  def options = [:]

  def option_names = [
    'kube_cluster_name',
    'kube_namespace',
    'kube_replicaset_name'
  ]

  def option_defaults = [
    'kube_cluster_name': null,
    'kube_namespace': null,
    'kube_replicaset_name': null
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
  echo "option kube_cluster_name = ${options['kube_cluster_name']}"
  echo "option kube_replicaset_name = ${options['kube_replicaset_name']}"
}

return this;
