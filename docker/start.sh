#!/usr/bin/env bash
set -e 

key="$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 64 | head -n 1)" 

cat <<EOF > /opt/docker/conf/local-dev.conf
play.http.secret.key="$key"

app.webgroup.prefix="in-"\${app.name.id}"-local-dev-"

play.filters.disabled+=play.filters.hosts.AllowedHostsFilter
EOF

export PLAY_HTTPS_PORT=8443

/opt/docker/bin/play -Dpidfile.path=/dev/null -Dconfig.file=/opt/docker/conf/application.conf
