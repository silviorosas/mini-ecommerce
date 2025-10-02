#!/bin/bash
# Espera a que el servidor de Eureka esté disponible
set -e

host="$1"
shift
cmd="$@"

until curl -s "$host/actuator/health" | grep 'UP'; do
  >&2 echo "El servidor de Eureka no está disponible - esperando..."
  sleep 1
done

>&2 echo "El servidor de Eureka está UP - ¡iniciando la aplicación!"
exec $cmd
