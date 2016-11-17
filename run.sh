previous_pid=`cat pid`

if [ ! -z "$previous_pid" ]; then
	echo "Shuting down running application with pid $previous_pid"
	kill -9 $previous_pid
fi

echo "Waiting 20 seconds to check application's health"
sleep 2s

java -Dspring.profiles.active=itba -jar news-visualization.jar &
pid=$!
disown

sleep 20s
echo "Checking status..."

status=`curl -s -XGET "http://localhost:9090/news-visualization/api/health" | \
    python3 -c "import sys, json; print(json.load(sys.stdin)['status'])"`

if [ $status == 'UP' ]; then
	echo "Server is UP with pid $pid"
else
	echo "Server is DOWN, try again"
fi

echo $pid > pid
