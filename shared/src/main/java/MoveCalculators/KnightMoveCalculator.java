package MoveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class KnightMoveCalculator extends MoveCalculators{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        int [][] posMoves = {{2,1},{1,2},{-2,1},{1,-2},{-2,-1},{-1,-2},{-1,2},{2,-1}};
        return MoveCalculators.generateMoves(board,myPosition,posMoves);}
}
