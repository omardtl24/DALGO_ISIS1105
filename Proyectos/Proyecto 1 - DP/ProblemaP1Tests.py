import sys

# ----------------------------------------------------------------------------------------
#Definimos las funciones que nos apoyarán en el desarrollo del problema

def IndianaPath(A):
    '''
        Calcula el maximo camino para Indiana Jones dada una matriz de entrada A.

        Parametros:
            - A (list of lists): Matriz que representa la cantidad de reliquias o maldicion en cada casilla
        
        Retorna:
            - max_ (int): Cantidad máxima de reliquias recogidas por Indiana Jones
            - A (list of lists): Matriz actualizada con el camino realizado por Indiana Jones (Reliquias recogidas)

        Complejidad: Dada la matriz de entrada de dimensiones R x C
            - Temporal: T(R,C) ∈ O(RC)
            - Espacial: S(R,C) ∈ O(RC)
    '''
    #Instancia estructura para aplicar DP
    R,C = len(A) , len(A[0])
    I = [[0 for _ in range(C)] for _ in range(R)]
    inf = int(1e6)
    #Aplicacion de la recurrencia y memoización
    for r in range(0,R):
        for i in range(0,C):
            if r==0 and i==0: I[r][i] = 0
            elif A[r][i]==-1 or (r==0 and i>0): I[r][i] = -inf
            elif r>0 and i==0: I[r][i] = max(I[r-1][i],I[r-1][i+1]) + A[r][i]
            elif r>0 and i==C-1: I[r][i] = max(I[r-1][i],I[r-1][i-1]) + A[r][i]
            elif r>0 and 0<i<C-1: I[r][i] = max(I[r-1][i],I[r-1][i-1],I[r-1][i+1]) + A[r][i]
    row = R//2
    #Obtiene la casilla de salida de Indiana y recupera la cantidad de reliquias recogida
    max_ , c = I[row][0], 0
    for column in range(1,C):
        if I[row][column] > max_:
            max_ = I[row][column]
            c = column
    if max_ < 0: return 0 , A
    #Reconstruye el camino usado por Indiana utilizando Bakctracking
    r = row
    i = c 
    while(r!=0):
        k = A[r][i]
        A[r][i] = 0
        if I[r-1][i] + k != I[r][i]:
            moves = []
            if i>0:
                moves.append((r-1,i-1))
            if i<C-1:
                moves.append((r-1,i+1))
            for row,column in moves:
                if I[row][column] + k == I[r][i]:
                    i = column
                    break
        r-=1
    return max_, A


def MarionPath(A):
    '''
        Calcula el maximo camino para Marion dada una matriz de entrada A.

        Parametros:
            - A (list of lists): Matriz que representa la cantidad de reliquias o maldicion en cada casilla
        
        Retorna:
            - max_ (int): Cantidad máxima de reliquias recogidas por Marion
            - A (list of lists): Matriz actualizada con el camino realizado por Marion (Reliquias recogidas)
            
        Complejidad: Dada la matriz de entrada de dimensiones R x C
            - Temporal: T(R,C) ∈ O(RC)
            - Espacial: S(R,C) ∈ O(RC)
    '''
    R,C = len(A) , len(A[0])
    M = [[0 for _ in range(C)] for _ in range(R)]
    inf = int(1e6)
    for r in range(0,R):
        for i in range(0,C):
            if r==0 and i==C-1: M[r][i] = 0
            elif A[r][i]==-1 or (r==0 and i<C-1): M[r][i] = -inf
            elif r>0 and i==0: M[r][i] = max(M[r-1][i],M[r-1][i+1]) + A[r][i]
            elif r>0 and i==C-1: M[r][i] = max(M[r-1][i],M[r-1][i-1]) + A[r][i]
            elif r>0 and 0<i<C-1: M[r][i] = max(M[r-1][i],M[r-1][i-1],M[r-1][i+1]) + A[r][i]
    row = R//2
    max_ , c = M[row][0], 0
    for column in range(1,C):
        if M[row][column] > max_:
            max_ = M[row][column]
            c = column
    if max_ < 0: return 0 , A
    #Backtrack
    r = row
    i = c 
    while(r!=0):
        k = A[r][i]
        A[r][i] = 0
        if M[r-1][i] + k != M[r][i]:
            moves = []
            if i>0:
                moves.append((r-1,i-1))
            if i<C-1:
                moves.append((r-1,i+1))
            for row,column in moves:
                if M[row][column] + k == M[r][i]:
                    i = column
                    break
        r-=1
    return max_, A


def SalahPath(A):
    '''
        Calcula el maximo camino para Salah dada una matriz de entrada A.

        Parametros:
            - A (list of lists): Matriz que representa la cantidad de reliquias o maldicion en cada casilla
        
        Retorna:
            - max_ (int): Cantidad máxima de reliquias recogidas por Salah
            - A (list of lists): Matriz actualizada con el camino realizado por Salah (Reliquias recogidas)
        
        Complejidad: Dada la matriz de entrada de dimensiones R x C
            - Temporal: T(R,C) ∈ O(RC)
            - Espacial: S(R,C) ∈ O(RC)
    '''
    R,C = len(A) , len(A[0])
    S = [[0 for _ in range(C)] for _ in range(R)]
    inf = int(1e6)
    for r in range(R-1,-1,-1):
        for i in range(C-1,-1,-1):
            if r==R-1 and i==C//2: S[r][i] = 0
            elif A[r][i]==-1 or (r==R-1 and i!=C//2): S[r][i] = -inf
            elif r<R-1 and i==0: S[r][i] = max(S[r+1][i],S[r+1][i+1]) + A[r][i]
            elif r<R-1 and i==C-1: S[r][i] = max(S[r+1][i],S[r+1][i-1]) + A[r][i]
            elif r<R-1 and 0<i<C-1: S[r][i] = max(S[r+1][i],S[r+1][i-1],S[r+1][i+1]) + A[r][i]
    r = int(R/2)
    max_ , c = S[r][0], 0
    for column in range(1,C):
        if S[r][column] > max_:
            max_ = S[r][column]
            c = column
    if max_ < 0: return 0 , A
    #Backtrack
    i = c 
    while(r!=R-1):
        k = A[r][i]
        A[r][i] = 0
        if S[r+1][i] + k != S[r][i]:
            moves = []
            if i>0:
                moves.append((r+1,i-1))
            if i<C-1:
                moves.append((r+1,i+1))
            for row,column in moves:
                if S[row][column] + k == S[r][i]:
                    i = column
                    break
        r+=1
    return max_, A

def areDisjoint(A ,A_copies):
    '''
        Verifica que los caminos por los personajes sean disjuntos. 
        
        Definimos que los caminos son disjuntos si no existe un cero que se coordine entre las matrices
        
        (Es decir que los caminos son disjuntos si un solo personaje recogió las reliquias de una casilla 
        particular)

        Parametros:
            - A (list of lists): Matriz antes de que los personajes entraran a recuperar reliquias
            - A (list of lists of lists): Matrices que representan los caminos de cada personaje
        
        Retorna:
            - (boolean): Booleano que representa si las matrices son disjuntas o no
        
        Complejidad: Dada la matriz de entrada de dimensiones R x C
            - Temporal: T(R,C) ∈ O(RC)
            - Espacial: S(R,C) ∈ O(1)
    '''
    R,C = len(A) , len(A[0])
    for r in range(1,R-1):
        for c in range(0,C):
            zeros = 0
            #Verifica si hay ceros alineados entre las matrices
            for A_c in A_copies:
                if A_c[r][c]==0 and A[r][c]!=0: zeros+=1
            #Si existen ceros alineados las matrices no son disjuntas
            if zeros>1: return False
    #Si se recorre toda la matriz sin identificar irregularidades la matriz es disjunta
    return True

def copyMatrix(A):
    '''
        Genera una copia independiente de una matriz de entrada A

        Parametros:
            - A (list of lists): Matriz que busca ser clonada
        
        Retorna:
            - r (list of lists): Copia identica de la matriz de entrada A

        Complejidad: Dada la matriz de entrada de dimensiones R x C
            - Temporal: T(R,C) ∈ O(RC)
            - Espacial: S(R,C) ∈ O(RC)
    '''
    R,C = len(A) , len(A[0])
    r =  [[A[i][j] for j in range(C)] for i in range(R)]
    return r

def getMax2(A, callable1, callable2):
    '''
        Soluciona el problema de maximizar la recoleccion de reliquias para dos personajes

        Parametros:
            - A (list of lists): Matriz que representa la cantidad de reliquias o maldicion en cada casilla
        
        Retorna:
            - (int): Cantidad máxima de reliquias que pueden recoger los dos personajes
        
        Complejidad: Dada la matriz de entrada de dimensiones R x C
            - Temporal: T(R,C) ∈ O(RC)
            - Espacial: S(R,C) ∈ O(RC)
    '''
    #Calcula los caminos de cada personaje sobre la matriz original
    m1 , A1 = callable1(copyMatrix(A))
    m2 , A2 = callable2(copyMatrix(A))
    #Verifica que los caminos sean disyuntos
    if areDisjoint(A,[A1,A2]):
        #Si los caminos son disyuntos retorna la suma de los tres
        return m1+m2
    #Si no son disyuntos, computa el orden de los demás caminos y recupera el máximo
    return max(
        m1 + callable2(A1)[0],
        m2 + callable1(A2)[0]
    )

def getMax3(A, callable1, callable2, callable3):
    '''
        Soluciona el problema de maximizar la recoleccion de reliquias para tres personajes

        Parametros:
            - A (list of lists): Matriz que representa la cantidad de reliquias o maldicion en cada casilla
        
        Retorna:
            - (int): Cantidad máxima de reliquias que pueden recoger los tres personajes
        
        Complejidad: Dada la matriz de entrada de dimensiones R x C
            - Temporal: T(R,C) ∈ O(RC)
            - Espacial: S(R,C) ∈ O(RC)
    '''
    #Calcula los caminos de cada personaje sobre la matriz original
    m1 , A1 = callable1(copyMatrix(A))
    m2 , A2 = callable2(copyMatrix(A))
    m3 , A3 = callable3(copyMatrix(A))
    #Verifica que los caminos sean disyuntos
    if areDisjoint(A,[A1,A2,A3]):
        #Si los caminos son disyuntos retorna la suma de los tres
        return m1+m2+m3
    #Si no son disjuntos, computa el orden de los demás caminos y recupera el máximo
    return max(
        m1 + getMax2(A1,callable2, callable3),
        m2 + getMax2(A2,callable1,callable3),
        m3 + getMax2(A3,callable1,callable2)
    )

def solvePathsProblem(A):
    '''
        Define la salida para el evaluador del problema de maximización de reliquias.

        En el caso de que los tres personajes queden atrapados en la piramide la función de conteo de reliquias
        devolverá de resultado 0. Sin embargo, si este escenario sucede, se debe retornar -1.

        Sin embargo, se debe tener en cuenta que con el caso de entrada (Una unica fila y tres columnas):

        A = [
            [0,0,0]
        ]

        el resultado es el mismo 0, ya que los personajes lograrón salir de la piramide con vida pero no recogieron reliquias.
        Este caso es posible definirlo de esta forma dado que los demás valores de la matriz no pueden ser ceros, por lo que ese
        es el único escenario plausible en el que el resultado puede ser cero.

        Parametros:
            - A (list of lists): Matriz que representa la cantidad de reliquias o maldicion en cada casilla
        
        Retorna:
            - (int): Respuesta al problema de maximizacion de reliquias dado el formato definido
        
        Complejidad: Dada la matriz de entrada de dimensiones R x C
            - Temporal: T(R,C) ∈ O(RC)
            - Espacial: S(R,C) ∈ O(RC)
    '''
    R,C = len(A) , len(A[0])
    answer = getMax3(A,IndianaPath,MarionPath,SalahPath)
    if R==1 or answer>0: return answer
    return -1