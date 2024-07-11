
v0.0.4: Dev proxy server setup


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

Run dev server:\
`npm run dev`


### Model

Compile protobuf types:\
`protoc --java_out=../backend/src/main/java/fyi/lnz/psych_constructs/model/proto ./test.proto`