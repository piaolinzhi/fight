set -x
echo $0
echo "The first args of shell is $0"
echo "The number of args is $#"
echo "The pid of the shell is $$"
echo "The return value of last command is $?"
echo "The pid of last command is $!"
echo "The args of the shell is $*"
echo "The second args of shell is $1"
if [ $# -gt 0 -a -n $1 ] 
then
	echo $1
else 
	echo "no args1"
fi
