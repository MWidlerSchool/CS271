package VMCompilerMkII;

// a class which represents each line of VM code as either a constant, or two constants and an int
public class VMLine
{
    public final VMConstants arg1;
    public final VMConstants arg2;
    public final int arg3;
    public final int arguments;
    
    public VMLine(String str)
    {
        String strArr[] = str.split(" ");
        arguments = strArr.length;
        arg1 = VMConstants.get(strArr[0]);
        if(arguments > 1)
        {
            arg2 = VMConstants.get(strArr[1]);
            arg3 = Integer.parseInt(strArr[2]);
        }
        else
        {
            arg2 = null;
            arg3 = 0;
        }
    }
}