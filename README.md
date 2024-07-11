
v0.0.1: Hello world backend

### Backend

##### Setup virtual env

First time you'll need to create a python virtual env:\
`python -m venv ./django_venv`\
Then activate it:\
`source ./django_venv/bin/activate`\
Now install backend dependencies in the virtual env:\
`pip install -r ./requirements.txt`

##### Start the backend service

Start the django server by first entering the virtual env\
`source ./django_venv/bin/activate`\
Then run the django script\
`python ./manage.py runserver`

##### Bash scripts (linux)

Script to setup or reset virtual env:\
`bash ./reset_venv.sh`\
Script to start the service:\
`bash ./serve.sh`

### Frontend
