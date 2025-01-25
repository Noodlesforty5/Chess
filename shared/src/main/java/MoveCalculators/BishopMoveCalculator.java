package MoveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class BishopMoveCalculator extends MoveCalculators {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        int [][] posMoves = {{1,1},{-1,1},{1,-1},{-1,-1}};
        return MoveCalculators.generateDiagMoves(board, myPosition, posMoves);}
}
