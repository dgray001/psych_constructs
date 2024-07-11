sudo rm -r ./django_venv
python -m venv ./django_venv
source ./django_venv/bin/activate
pip install --upgrade pip
pip install -r ./requirements.txt
deactivate