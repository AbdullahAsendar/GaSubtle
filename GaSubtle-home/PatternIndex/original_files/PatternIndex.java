// This is a mutant program.
// Author : ysma

public class PatternIndex
{

    public static  void main( java.lang.String[] argv )
    {
        if (argv.length != 2) {
            System.out.println( "java PatternIndex Subject Pattern" );
            return;
        }
        java.lang.String subject = argv[0];
        java.lang.String pattern = argv[1];
        int n = 0;
        if ((n = patternIndex( subject, pattern )) == -1) {
            System.out.println( "Pattern string is not a substring of the subject string" );
        } else {
            System.out.println( "Pattern string begins at character " + n );
        }
    }

    public static  int patternIndex( java.lang.String subject, java.lang.String pattern )
    {
        final int NOTFOUND = -1;
        int iSub = 0;
        int rtnIndex = NOTFOUND;
        boolean isPat = false;
        int subjectLen = subject.length();
        int patternLen = pattern.length();
        while (isPat == false && iSub + patternLen - 1 < subjectLen) {
            if (subject.charAt( iSub ) == pattern.charAt( 0 )) {
                rtnIndex = iSub;
                isPat = true;
                for (int iPat = 1; iPat < patternLen; iPat++) {
                    if (subject.charAt( iSub + iPat ) != pattern.charAt( iPat )) {
                        rtnIndex = NOTFOUND;
                        isPat = false;
                        break;
                    }
                }
            }
            iSub++;
        }
        return rtnIndex;
    }

}
