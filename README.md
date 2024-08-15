
v0.1.14 Update/Delete Construct api


### Backend

Run dev server:\
`mvn -Dspring-boot.run.arguments="--environment=DEV" spring-boot:run`

Run packaged:\
`mvn compile`\
`java -jar target/backend-0.0.1-SNAPSHOT.jar`

The database connection for dev is hardcoded for now until the prod infrastructure is set up


### Frontend

Install node modules:\
`npm i`

Format code:\
`npm run format`

Build static site:\
`npm run build`

Run dev server:\
`npm run dev`


### Model

Compile protobuf types to backend (from model directory):\
`protoc --java_out=../backend/src/main/java ./*.proto`

Compile protobuf types to backend (from frontend directory):\
`npx protoc --ts_out ./proto --proto_path ../model ../model/*.proto`

Note if a proto gets deleted you will need to manually remove it from the proto out folders. To accomplish this in script just delete all out files before compiling

Also note a bash script is provided (compile.sh)
