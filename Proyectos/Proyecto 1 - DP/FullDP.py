def getSum(A,r,i,m,s,bi,bm,bs):
    R,C = len(A) , len(A[0])
    b1 = bi == 1
    b2 = bm == 1
    b3 = bs == 1
    mi,mm,ms = A[r][i],A[r][m],A[R-1-r][s]
    #Sale solo 1
    if not b1 and not b2 and not b3: return 0
    if b1 and not b2 and not b3: return mi
    if not b1 and b2 and not b3: return mm
    if not b1 and not b2 and b3: return ms
    #Salen solo dos
    if b1 and b2 and not b3:
        #Indiana y Marion Colisionan
        if i==m: return mi
        #No colisionan de ninguna forma
        return mi+mm
    if b1 and not b2 and b3:
        #Indiana y Salah colisionan en la mitad
        if r==R//2 and i==s: return ms
        #No colisionan de ninguna forma
        return mi+ms
    if not b1 and b2 and b3:
        #Marion y Salah colisionan en la mitad
        if r==R//2 and m==s: return ms
        #No colisionan de ninguna forma
        return mm+ms
    #Salen los tres
    #Indiana y Marion Colisionan antes de llegar a la mitad
    if r<R//2 and i==m: return mi+ms
    #Los tres colisionan en la mitad
    if r==R//2 and i==m and m==s:
        return ms
    #Marion y Salah colisionan en la mitad
    if r==R//2 and i!=m and m==s: return ms + mi
    #Indiana y Salah colisionan en la mitad
    if r==R//2 and i!=m and i==s: return ms + mm
    #Indiana y Marion colisionan en la mitad
    if r==R//2 and i==m and i!=s and m!=s: return ms + mm
    #No colisionan de ninguna forma
    return mi+mm+ms

def get_max(nested_list):
    max_val = float('-inf')
    for item in nested_list:
        if isinstance(item, list):
            max_val = max(max_val, get_max(item))
        else:
            max_val = max(max_val, item)
    return max_val

def fullSolutionDP(A):
    R,C = len(A) , len(A[0])
    P = [[[[[[[0 for _ in range(2)] for _ in range(2)] for _ in range(2)] 
        for _ in range(C)] for _ in range(C)] for _ in range(C)] for _ in range(R)]
    inf = int(1e7)
    for bi in range(2):
        for bm in range(2):
            for bs in range(2):
                for r in range(R//2 + 1):
                    for i in range(C):
                        for m in range(C):
                            for s in range(C):
                                if bi==0 and bm==0 and bs==0:
                                    P[r][i][m][s][bi][bm][bs] = -1
                                if bi==0 and bm==0 and bs==1:
                                    if r==0 and s==C//2: P[r][i][m][s][bi][bm][bs] = 0
                                    elif (r==0 and s!=C//2) or (A[R-1-r][s]==-1): P[r][i][m][s][bi][bm][bs] = -inf

                                    elif r>0 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)


                                if bi==0 and bm==1 and bs==0:
                                    if r==0 and m==C-1: P[r][i][m][s][bi][bm][bs] = 0
                                    elif (r==0 and m<C-1) or (A[r][m] == -1): P[r][i][m][s][bi][bm][bs] = -inf

                                    elif r>0 and m==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and m==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<m<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                if bi==0 and  bm==1 and  bs==1:
                                    if r==0 and s==C//2 and m==C-1: P[r][i][m][s][bi][bm][bs] = 0
                                    elif (r==0 and (s!=C//2 or m<C-1)) or (A[r][m] == -1 or A[R-1-r][s]==-1): P[r][i][m][s][bi][bm][bs] = -inf
                                
                                    elif r>0 and m==0 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and m==0 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and m==C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and m==C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and m==0 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and m==C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<m<C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<m<C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                 
                                    elif r>0 and 0<m<C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)  
                                
                                
                                if bi==1 and bm==0 and bs==0:
                                    if r==0 and i==0: P[r][i][m][s][bi][bm][bs] = 0
                                    elif (r==0 and i>0) or (A[r][i] == -1): P[r][i][m][s][bi][bm][bs] = -inf

                                    elif r>0 and i==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                
                                if bi==1 and bm==0 and bs==1:
                                    if r==0 and i==0 and s==C//2: P[r][i][m][s][bi][bm][bs] = 0
                                    elif (r==0 and (i>0 or s!=C//2)) or (A[r][i] == -1 or A[R-1-r][s]==-1): P[r][i][m][s][bi][bm][bs] = -inf

                                    elif r>0 and i==0 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==0 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==0 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                 
                                    elif r>0 and 0<i<C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)                             
                                         
                                if bi==1 and bm==1 and bs==0:
                                    if r==0 and i==0 and m==C-1: P[r][i][m][s][bi][bm][bs] = 0
                                    elif (r==0 and (i>0 or m<C-1)) or (A[r][i] == -1 or A[r][m] == -1): P[r][i][m][s][bi][bm][bs] = -inf
                                
                                    elif r>0 and i==0 and m==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and i==0 and m==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and i==C-1 and m==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and m==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==0 and 0<m<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and 0<m<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1 and m==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1 and m==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                 
                                    elif r>0 and 0<i<C-1 and 0<m<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                if bi==1 and  bm==1 and  bs==1:
                                    if r==0 and i==0 and s==C//2 and m==C-1:
                                        P[r][i][m][s][bi][bm][bs] = 0
                                    elif (r==0 and (i>0 or s!=C//2 or m<C-1)) or (r>0 and (A[r][i] == -1 or A[r][m] == -1 or A[R-1-r][s]==-1)): P[r][i][m][s][bi][bm][bs] = -inf
                                    elif r>0 and i==0 and m==0 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and i==0 and m==0 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and i==0 and m==C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and i==C-1 and m==0 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and i==0 and m==C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and i==C-1 and m==0 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and m==C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and m==C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==0 and m==0 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and m==0 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==0 and m==C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and m==C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==0 and 0<m<C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and 0<m<C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    
                                    elif r>0 and i==0 and 0<m<C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and i==C-1 and 0<m<C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1 and m==0 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1 and m==0 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1 and m==C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)

                                    elif r>0 and 0<i<C-1 and m==C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)


                                    elif r>0 and i==0 and 0<m<C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and i==C-1 and 0<m<C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],                                          
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],                                            
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s+1][bi][bm][bs],                                           
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s+1][bi][bm][bs],                                            
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],                                            
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s-1][bi][bm][bs],                        
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and 0<i<C-1 and m==0 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and 0<i<C-1 and m==C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and 0<i<C-1 and 0<m<C-1 and s==0:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s+1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and 0<i<C-1 and 0<m<C-1 and s==C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
                                    elif r>0 and 0<i<C-1 and 0<m<C-1 and 0<s<C-1:
                                        P[r][i][m][s][bi][bm][bs] = max(
                                            P[r-1][i][m][s][bi][bm][bs],
                                            P[r-1][i-1][m][s][bi][bm][bs],
                                            P[r-1][i+1][m][s][bi][bm][bs],
                                            P[r-1][i][m-1][s][bi][bm][bs],
                                            P[r-1][i-1][m-1][s][bi][bm][bs],
                                            P[r-1][i+1][m-1][s][bi][bm][bs],
                                            P[r-1][i][m+1][s][bi][bm][bs],
                                            P[r-1][i-1][m+1][s][bi][bm][bs],
                                            P[r-1][i+1][m+1][s][bi][bm][bs],
                                            P[r-1][i][m][s+1][bi][bm][bs],
                                            P[r-1][i-1][m][s+1][bi][bm][bs],
                                            P[r-1][i+1][m][s+1][bi][bm][bs],
                                            P[r-1][i][m-1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s+1][bi][bm][bs],
                                            P[r-1][i][m+1][s+1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s+1][bi][bm][bs],
                                            P[r-1][i][m][s-1][bi][bm][bs],
                                            P[r-1][i-1][m][s-1][bi][bm][bs],
                                            P[r-1][i+1][m][s-1][bi][bm][bs],
                                            P[r-1][i][m-1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m-1][s-1][bi][bm][bs],
                                            P[r-1][i][m+1][s-1][bi][bm][bs],
                                            P[r-1][i-1][m+1][s-1][bi][bm][bs],
                                            P[r-1][i+1][m+1][s-1][bi][bm][bs]
                                        ) + getSum(A,r,i,m,s,bi,bm,bs)
    
    r = get_max(P[R//2])
    return r , P