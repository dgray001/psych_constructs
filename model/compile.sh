
rm ../backend/src/main/java/proto/*
protoc --java_out=../backend/src/main/java ./*.proto

cd ../frontend
rm ./proto/*
npx protoc --ts_out ./proto --proto_path ../model ../model/*.proto
cd ../model
