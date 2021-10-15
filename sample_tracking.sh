echo "Building Sample Tracking Central app for production ....";
./mvnw clean install -DskipTests -Dspring.profiles.active=prod
docker-compose build sampletracking
docker-compose up -d sampletracking
echo "Done building sample tracking :)";
docker-compose ps
