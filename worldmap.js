destW = 256
destH = 160
resArr = []
nMax = 16
pile = (nMax * 0.4)|0

 srcWU = (destW / 20 - 1)|0
 srcHU = (destH / 20 - 1)|0

for (n = 0; n < nMax; n++) {
     srcW = srcWU * (n + 1)
     srcH = srcHU * (n + 1)
     srcArr = []
     for ( i = 0; i < srcW * srcH; i ++) srcArr[i] =  ((i * i * 4999 + 8999 & 0xFFFF) / 0x10000) % 2 * 255
     destArr = [],scaleX = destW / (srcW - 1),scaleY = destH / (srcH - 1)

    for ( y = 0; y < destH; y++) {
        for ( x = 0; x < destW; x++) {
             x0 = (x / scaleX)|0
             y0 = (y / scaleY)|0

             x1 = x / scaleX - x0
             y1 = y / scaleY - y0

             col0 = srcArr[x0     + (y0    ) * srcW]
             col1 = srcArr[x0 + 1 + (y0    ) * srcW]
             col2 = srcArr[x0     + (y0 + 1) * srcW]
             col3 = srcArr[x0 + 1 + (y0 + 1) * srcW]

                [x + y * destW] = (
                  (1 - x1) * (1 - y1) * col0
                +      x1  * (1 - y1) * col1
                + (1 - x1) *      y1  * col2
                +      x1  *      y1  * col3
            )|0
        }
    }

     plRes = (n + pile) / (n + pile + 1)
     plDest = 1 / (n + pile + 1)

    for ( i = 0; i < destW * destH; i ++) resArr[i] = n == 0 ? destArr[i] :  resArr[i] = (resArr[i] * plRes + destArr[i] * plDest)|0
}


return destW + "," + destH + "," + resArr.join(",")
