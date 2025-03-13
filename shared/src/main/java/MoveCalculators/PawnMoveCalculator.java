package MoveCalculators;

import chess.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class PawnMoveCalculator extends MoveCalculators {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = HashSet.newHashSet(16);
        int curRow = myPosition.getRow();
        int curCol = myPosition.getColumn();
        ChessPiece.PieceType[] promotionPieces = new ChessPiece.PieceType[]{null};
        ChessGame.TeamColor myTeam = board.getTeam(myPosition);
        boolean promote = (myPosition.getRow() == 7 && myTeam == ChessGame.TeamColor.WHITE) || (myPosition.getRow() == 2 && myTeam == ChessGame.TeamColor.BLACK);
        if (promote){
            promotionPieces = new ChessPiece.PieceType[]{ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.ROOK};
        }
        int movement = 1;
        if (board.getTeam(myPosition) == ChessGame.TeamColor.BLACK){
            movement = -1;
        }


        for (ChessPiece.PieceType promotionPiece : promotionPieces) {
                // Basic Move
                ChessPosition basicMove = new ChessPosition(curRow + movement, curCol);
                if ((MoveCalculators.canMove(basicMove) && board.getPiece(basicMove) == null)){
                        moves.add(new ChessMove(myPosition, basicMove, promotionPiece));
                }
                // Attack Move
                ChessPosition attackLeft = new ChessPosition(curRow + movement, curCol - 1);
                if ((MoveCalculators.canMove(attackLeft) && board.getTeam(attackLeft) != myTeam) && board.getPiece(attackLeft) != null ) {
                        moves.add(new ChessMove(myPosition, attackLeft, promotionPiece));
                    }

                ChessPosition attackRight = new ChessPosition(curRow + movement, curCol + 1);

                if (MoveCalculators.canMove(attackRight) && (board.getTeam(attackRight) != myTeam)  && board.getPiece(attackRight) != null) {
                    moves.add(new ChessMove(myPosition, attackRight, promotionPiece));
                }

            }


            // Jumpstart
            if (myPosition.getRow() == 7 && myTeam == ChessGame.TeamColor.BLACK|| myPosition.getRow() == 2 && myTeam == ChessGame.TeamColor.WHITE){
                    ChessPosition jumpStart = new ChessPosition(curRow + 2*(movement),curCol);
                    ChessPosition Front = new ChessPosition(curRow + movement, curCol);
                    if (board.getPiece(Front) == null && board.getPiece(jumpStart) == null && MoveCalculators.canMove(Front) && MoveCalculators.canMove(jumpStart)){
                        moves.add(new ChessMove(myPosition, jumpStart,null));
                    }
                }

            return (moves != null) ? moves : Collections.emptyList();
        }

    }

