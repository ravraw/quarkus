#!/bin/bash
#
# Hit url every 1 sec for 15 sec.
#
end=$((SECONDS+15))

while [ $SECONDS -lt $end ];
do
    echo "\n $SECONDS hitting GET to fetch remote users ... ";
    curl "http://localhost:8080/remote-users" ;
    sleep 1;
done

