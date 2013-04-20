/*! Arithmetric Optimization Test */

#include <stdio.h>
#include <math.h>

/*! 2をN回掛ける
    @param[in]      opt 最適化レベル
    @param[in]      N   掛ける回数(0以上30以下)
    @return         計算結果
*/
int multiply(const int opt, const int N)
{
    int n, result = 1;

    if(opt==0)
    {
        /* 最適化前 : 2をN回valueに掛けます */
        for(n=0; n<N; n++)
        {
            result *= 2;
        }
    }
    else if(opt==1)
    {
        /* 最適化後 : 引数に依存しないように最適化してください */
        result = result << N;
    }
    return result;
}

/*! 符号付き整数Nの絶対値が閾値T以内か
    @param[in]      opt 最適化レベル
    @param[in]      N   符号付き整数  
    @param[in]      T   閾値(0以上2^30以下)
    @return         計算結果
*/
int within(const int opt, const int N, const unsigned int T)
{
    if(opt==0)
    {
        /* 最適化前 : 条件分岐を２回用います */
        if(N >= -(int)T && N <= (int)T) return 1;
    }
    else if(opt==1)
    {
        /* 最適化後 : 引数に依存しないように条件分岐を減らしてください */
        if(fabs(N) <= T) return 1;
    }
    return 0;
}


int main(int argc, char *argv[])
{
    int opt;

    printf("multiply 1 by 2 a number of times\n");
    for(opt=0; opt<=1; ++opt)
    {
        printf(" (optimize:%d) result=%d\n", opt, multiply(opt, 24));
    }

    printf("check if abstract value N within threshold T\n");
    for(opt=0; opt<=1; ++opt)
    {
        printf(" (optimize:%d) result=%d\n", opt, within(opt, -3, 3));
        printf(" (optimize:%d) result=%d\n", opt, within(opt, 3, 3));
        printf(" (optimize:%d) result=%d\n", opt, within(opt, -4, 3));
        printf(" (optimize:%d) result=%d\n", opt, within(opt, 4, 3));
    }

    return 0;
}