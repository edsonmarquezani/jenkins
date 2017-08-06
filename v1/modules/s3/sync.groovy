def get_options(opts){

  def options = [:]

  def option_names = [
    's3_bucket',
    's3_region',
    's3_prefix',
    'source_dir'
  ]

  def option_defaults = [
    's3_bucket': null,
    's3_region': 'sa-east-1',
    's3_prefix': "",
    'source_dir': "./"
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

def options = get_options(module_opts)

echo "option s3_bucket = ${options['s3_bucket']}"
echo "option s3_region = ${options['s3_region']}"
echo "option s3_prefix = ${options['s3_prefix']}"
echo "option source_dir = ${options['source_dir']}"
