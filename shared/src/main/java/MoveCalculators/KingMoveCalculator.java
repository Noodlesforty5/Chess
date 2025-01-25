package MoveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class KingMoveCalculator extends MoveCalculators{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        int [][] posMoves = {{1,1},{0,1},{1,0},{-1,0},{0,-1},{-1,-1},{-1,1},{1,-1}};
    return MoveCalculators.generateMoves(board,myPosition,posMoves);
    }
}
