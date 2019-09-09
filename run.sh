#!/bin/bash
export PLAY_HTTPS_PORT=8443
ls -lha target/assets/style.css 2>/dev/null 1>/dev/null || (npm ci && npm run build)
./sbt run