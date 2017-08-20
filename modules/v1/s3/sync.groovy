def get_options(opts){

  def options = [:]

  def option_names = [
    'bucket',
    'bucket_region',
    'key_prefix',
    'source_dir'
  ]

  def option_defaults = [
    'bucket': null,
    'bucket_region': 'sa-east-1',
    'key_prefix': '',
    'source_dir': './'
  ]

  // Getting module options and setting defaults
  def i=0;
  for (i=0; i < option_names.size(); i++) {
    name = option_names[i]
    value = (opts!=null && opts[name] != null) ? opts[name] : option_defaults[name]
    options.put (name, value)
  }

  return options
}

def main() {
  options = get_options(module_opts)
  echo "option bucket = ${options['bucket']}"
  echo "option bucket_region = ${options['bucket_region']}"
  echo "option key_prefix = ${options['key_prefix']}"
  echo "option source_dir = ${options['source_dir']}"
}

return this;
