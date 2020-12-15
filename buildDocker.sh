#!/bin/bash
ls -lha target/assets/style.css 2>/dev/null 1>/dev/null || (npm ci && npm run build)
./sbt docker:stage
./sbt docker:publishLocal
