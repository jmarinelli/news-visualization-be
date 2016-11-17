#!/bin/sh

DIR="$( cd "$( dirname "$0" )" && pwd )"
bin=${DIR}/../bin
lib=${DIR}/../lib

echo '{
    "type" : "jdbc",
    "jdbc" : {
        "url" : "jdbc:postgresql://localhost:5432/politica",
        "user" : "bigdatanow",
	"password": "bigdatanow",
        "sql" : "select * from entries order by id",
        "elasticsearch" : {
            "host" : "localhost",
            "port": "9300"
        }
    }
}
' | java \
    -cp "${lib}/*" \
    -Dlog4j.configurationFile=${bin}/log4j2.xml \
    org.xbib.tools.Runner \
    org.xbib.tools.JDBCImporter
