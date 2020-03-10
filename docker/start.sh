#!/usr/bin/env bash
set -e 

printf "play.http.secret.key=\"%s\"" "$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 64 | head -n 1)" | ( umask 337; cat > /opt/docker/conf/application.conf )

/opt/docker/bin/play -Dpidfile.path=/dev/null -Dconfig.file=/opt/docker/conf/application.conf