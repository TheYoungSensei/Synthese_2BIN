#! /bin/bash

# Local variable
logvar=10
echo Local variable logvar = $logvar

# Global variable
export GLOVAR=20
echo "Global (environment) variable GLOVAR = $GLOVAR"

echo "$0: pid = $$"

./prenv

exit 0
