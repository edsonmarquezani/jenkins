def get_options(opts){

  def options = [:]

  def option_names = [
    'akamai_distribution',
    'akamai_distribution_prefix'
  ]

  def option_defaults = [
   'akamai_distribution': null,
   'akamai_distribution_docroot': ""
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
  echo "option akamai_distribution = ${options['akamai_distribution']}"
  echo "option akamai_distribution_docroot = ${options['akamai_distribution_docroot']}"
}

return this;
