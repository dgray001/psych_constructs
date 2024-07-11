
v0.0.3: Protobuf and Vuejs frontend hello world


### Backend

Run dev server:\
`mvn spring-boot:run`

Run packaged:\
`mvn build`\
`java -jar target/backend-0.0.1-SNAPSHOT.jar`


### Frontend

Install node modules:\
`npm i`

Format code:\
`npm run format`

Build static site:\
`npm run build`


### Model

Compile protobuf types:\
`protoc --java_out=../backend/src/main/java/fyi/lnz/psych_constructs/model/proto ./test.proto`