# SSO Stub

This repo will contain a simple stub Single Sign-on server for local application development. The aim is to make working with
ITS web applications (e.g. [Tabula](https://github.com/UniversityofWarwick/tabula)) painless.

## Getting started

To start the development server on ports 8080 and 8443 (HTTP and self-signed auto TLS respectively), execute the `run.sh` script:

```
$ ./run.sh
```

<details>
<summary>Sytem requirements</summary>
Pre-requisites: working NPM and node (LTS is fine)

Supported platforms: macOS, Linux

</details>

The homepage will be accessible at `https://localhost:8443`. If you are using
an application with a built-in Docker environment (i.e. Tabula), it will assume you're using
sso-stub and set-up a virtual host for you.

Otherwise, you'll need to put a reverse proxy in front of `sso-stub` such that
something can listen on 443.

