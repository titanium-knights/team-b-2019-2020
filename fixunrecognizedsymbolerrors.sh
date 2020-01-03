#! /bin/sh

# See https://stackoverflow.com/questions/1885525/how-do-i-prompt-a-user-for-confirmation-in-bash-script

echo "Deleting $PWD/.gradle, $PWD/build, $PWD/FtcRobotController/build, $PWD/TeamCode/build"
read -p "Continue? [y/n] " -n 1 -r
echo

if [[ $REPLY =~ ^[Yy]$ ]]
then
  rm -rf .gradle
  rm -rf build
  rm -rf FtcRobotController/build
  rm -rf TeamCode/build
  echo "Done."
else
  echo "Aborting."
  exit 1 || return 1
fi