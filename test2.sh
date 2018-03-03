# Server implementation Tests
cd integrationtests/impl

home=$(pwd)

cd target/jre-dist2/bin/
./launch &
#sleep 3 # Give some time for the server to start

echo 'HOLA'
log="$home/target/jre-dist2/logs/server.log"

touch $log || exit
echo 'Waiting'
tail -F $log | grep -q "Server is running on port 8282"
echo 'Done!'

mvn test
pgrep -f eu.ggam.container | xargs kill -9
