package jump61;

import java.util.Random;

import static jump61.Side.*;

/** An automated Player.
 *  @author P. N. Hilfinger
 */
class AI extends Player {

    /** A new player of GAME initially COLOR that chooses moves automatically.
     *  SEED provides a random-number seed used for choosing moves.
     */
    AI(Game game, Side color, long seed) {
        super(game, color);
        _random = new Random(seed);
    }

    @Override
    String getMove() {
        Board board = getGame().getBoard();

        assert getSide() == board.whoseMove();
        int choice = searchForMove();
        getGame().reportMove(board.row(choice), board.col(choice));
        return String.format("%d %d", board.row(choice), board.col(choice));
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private int searchForMove() {
        Board work = getGame().getBoard();
        int value;
        assert getSide() == work.whoseMove();
        _foundMove = -1;
        if (getSide() == RED) {
            value = minMax(work, 4, true, 1, -1 * 1000, 1000);
        } else {
            value = minMax(work, 4, true, -1, -1 * 1000, 1000);
        }
        return _foundMove;
    }


    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int minMax(Board board, int depth, boolean saveMove,
                       int sense, int alpha, int beta) {
        int winningVal = board.size() * board.size();
        if (sense == 1) {
            if (board.getWinner() != null || depth == 0) {
                return staticEval(board, winningVal);
            }
            int bestSoFar = -1 * 1000;
            for (int i = 0; i < board.size() * board.size(); i += 1) {
                if (board.isLegal(board.whoseMove(), i)) {
                    board.addSpot(board.whoseMove(), i);
                    int response = minMax(board,
                            depth - 1, saveMove, -sense, alpha, beta);
                    if (response > bestSoFar) {
                        bestSoFar = response;
                        alpha = Math.max(alpha, bestSoFar);
                        if (alpha >= beta) {
                            return bestSoFar;
                        }
                    }
                    _foundMove = i;
                    board.undo();
                }
            }
            return bestSoFar;
        } else {
            if (board.getWinner() != null || depth == 0) {
                return staticEval(board, winningVal);
            }
            int bestSoFar = 1000;
            for (int i = 0; i < board.size() * board.size(); i += 1) {
                if (board.isLegal(board.whoseMove(), i)) {
                    board.addSpot(board.whoseMove(), i);
                    int response = minMax(board,
                            depth - 1, saveMove, -sense, alpha, beta);
                    if (response < bestSoFar) {
                        bestSoFar = response;
                        beta = Math.min(beta, bestSoFar);
                        if (alpha >= beta) {
                            return bestSoFar;
                        }
                    }
                    _foundMove = i;
                    board.undo();
                }
            }
            return bestSoFar;
        }
    }

    /** Return a heuristic estimate of the value of board position B.
     *  Use WINNINGVALUE to indicate a win for Red and -WINNINGVALUE to
     *  indicate a win for Blue. */
    private int staticEval(Board b, int winningValue) {
        if (b.whoseMove() == RED) {
            if (b.numOfSide(RED) == winningValue) {
                return winningValue;
            } else {
                return b.numOfSide(RED);
            }
        } else {
            if (b.numOfSide(BLUE) == winningValue) {
                return -winningValue;
            } else {
                return -b.numOfSide(BLUE);
            }
        }
    }

    /** A random-number generator used for move selection. */
    private Random _random;

    /** Used to convey moves discovered by minMax. */
    private int _foundMove;
}
