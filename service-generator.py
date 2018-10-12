import yaml
import argparse
import os
import shutil
import inspect
from distutils.dir_util import copy_tree
import subprocess


def main():
    parser = argparse.ArgumentParser(description='Reads yaml swagger and generates a service folder')
    parser.add_argument('swagger', metavar='swaggerFile', type=str, help='The swagger yaml file to read')
    parser.add_argument('output', metavar='output', type=str, help='The output directory')
    args = parser.parse_args()
    print('Swagger: {swagger} - Output: {output}'.format(swagger=args.swagger, output=args.output))
    print('Reading from the swagger...')

    project = ''
    module = ''

    with open(args.swagger, 'r') as stream:
        for line in stream:
            if '# Project:' in line:
                project = line.replace('# Project:', '').strip()
            elif '# Module:' in line:
                module = line.replace('# Module:', '').strip()

    print('Project={project} - Module={module}'.format(project=project, module=module))

    # project=HCP
    # module=CON-DS-SER

    full_name = ''
    with open(args.swagger, 'r') as swagger:
        try:
            loaded_yaml = yaml.load(swagger)
            # Content Domain Service
            full_name = loaded_yaml['info']['description']
        except yaml.YAMLError as e:
            print(e)

    # full_name=Content Domain Service

    template_map = [{
        'marker': '%PROJECT%',
        'value': project.lower()  # hcp : %PROJECT%
    }, {
        'marker': '%SERVICE-SHORT-1W%',
        'value': module.replace('-', '').lower()  # conbsser: %SERVICE-SHORT-1W%
    }, {
        'marker': '%SERVICE-NAME-SHORT-UPPER%',
        'value': module.replace('-', '_')  # CON_BS_SER: %SERVICE-NAME-SHORT-UPPER%
    }, {
        'marker': '%SERVICE-NAME%',
        'value': full_name.replace('Domain', 'DS').replace('Business', 'BS')  # Content DS Service: %SERVICE-NAME%
    }, {
        'marker': '%SERVICE-LONG-1W%',
        'value': full_name.replace('Domain', 'DS').replace('Business', 'BS').replace(' ', '').lower()
        # contentdsservice : %SERVICE-LONG-1W%
    }, {
        'marker': '%SERVICE-LONG%',
        'value': full_name.replace('Domain', 'DS').replace('Business', 'BS').replace(' ', '-').lower()
        # content-ds-service: %SERVICE-LONG%
    }]

    print(template_map)

    if os.path.exists(args.output):
        shutil.rmtree(args.output)
    os.makedirs(args.output)
    current_location = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
    template_location = current_location + '/template'
    copy_tree(template_location, args.output)
    shutil.copyfile(args.swagger, args.output + '/%PROJECT%.%SERVICE-LONG%/src/main/resources/swagger/' + full_name.replace('Domain', 'DS').replace('Business', 'BS').replace(' ', '-').lower() + '.yaml')

    to_be_renamed = []

    for subdir, dirs, files in os.walk(args.output):
        for directory in dirs:
            renamed_subdir = subdir
            for template_d in template_map:
                if template_d['marker'] in subdir:
                    renamed_subdir = renamed_subdir.replace(template_d['marker'], template_d['value'])
            renamed_directory = directory
            for template_d in template_map:
                if template_d['marker'] in directory:
                    renamed_directory = renamed_directory.replace(template_d['marker'], template_d['value'])
            to_be_renamed.append({
                'initial': renamed_subdir + '/' + directory,
                'renamed': renamed_subdir + '/' + renamed_directory
            })

        for file in files:
            content = ''
            with open(subdir + '/' + file) as f:
                content = f.read()

            for template_d in template_map:
                content = content.replace(template_d['marker'], template_d['value'])

            with open(subdir + '/' + file, 'w') as f:
                f.write(content)

            renamed_subdir = subdir
            for template_d in template_map:
                if template_d['marker'] in subdir:
                    renamed_subdir = renamed_subdir.replace(template_d['marker'], template_d['value'])
            renamed_file = file
            for template_d in template_map:
                if template_d['marker'] in file:
                    renamed_file = renamed_file.replace(template_d['marker'], template_d['value'])
            to_be_renamed.append({
                'initial': renamed_subdir + '/' + file,
                'renamed': renamed_subdir + '/' + renamed_file
            })

    for record_to_be_renamed in to_be_renamed:
        if '%' in record_to_be_renamed['initial'].split('/')[-1]:
            os.rename(record_to_be_renamed['initial'], record_to_be_renamed['renamed'])

    loaded_config = None
    with open(current_location + '/config/config.yaml') as config:
        try:
            loaded_config = yaml.load(config)

        except yaml.YAMLError as e:
            print(e)
    directory = args.output + '/' + project.lower() + '.' + full_name.replace('Domain', 'DS').replace('Business', 'BS').replace(' ', '-').lower()
    subprocess.call('cd {dir} && mvn clean generate-sources'.format(dir=directory), shell=True)
    name = loaded_config['git']['name']
    email = loaded_config['git']['email']
    repository = loaded_config['git']['repository']
    print('Git - Name={name} - Email={email} - Repository={repository}'.format(name=name, email=email, repository=repository))
    subprocess.call('cd {dir} && git init && git config user.name "{name}" && git config user.email "{email}"'.format(dir=directory, name=name, email=email), shell=True)
    subprocess.call('cd {dir} && git remote add origin {repository}'.format(dir=directory, repository=repository), shell=True)


if __name__ == '__main__':
    main()
