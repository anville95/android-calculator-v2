package io.github.anville95work.calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> expressionList = new ArrayList<String>();
    private String dynamicNumber = "";
    private boolean justEvaluated = false;

    private AppCompatButton powerButton;

    private static final String[] bootTextsArray = {
            "booting 005% #-------------------",
            "booting 010% ##------------------",
            "booting 015% ###-----------------",
            "booting 020% ####----------------",
            "booting 025% #####---------------",
            "booting 030% ######--------------",
            "booting 035% #######-------------",
            "booting 040% ########------------",
            "booting 045% #########-----------",
            "booting 050% ##########----------",
            "booting 055% ###########---------",
            "booting 060% ############--------",
            "booting 065% #############-------",
            "booting 070% ##############------",
            "booting 075% ###############-----",
            "booting 080% ################----",
            "booting 085% #################---",
            "booting 090% ##################--",
            "booting 095% ###################-",
            "booting 100% ####################"
    };

    private TextView expressionScreen;
    private TextView answerScreen;

    private static final double LOG_CONVERSION_FACTOR = 0.43429448190325;

    //Prevent excessive or inadequate closing brackets
    private int bracketsBalance = 0;
    private String result;

    //IS_CALC_ON_OR_OFF
    private boolean isCalculatorOn = false;
    //Do not interrupt the boot process
    private boolean isBooting = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialising the screens
        expressionScreen = findViewById(R.id.expression_screen);
        answerScreen = findViewById(R.id.answer_screen);
        answerScreen.setMovementMethod(new ScrollingMovementMethod());

        //Initialising buttons
        powerButton = findViewById(R.id.powerButton);

        AppCompatButton button7 = findViewById(R.id.button7);
        AppCompatButton button8 = findViewById(R.id.button8);
        AppCompatButton button9 = findViewById(R.id.button9);
        AppCompatButton buttonDiv = findViewById(R.id.buttonDiv);

        AppCompatButton button4 = findViewById(R.id.button4);
        AppCompatButton button5 = findViewById(R.id.button5);
        AppCompatButton button6 = findViewById(R.id.button6);
        AppCompatButton buttonX = findViewById(R.id.buttonX);

        AppCompatButton button1 = findViewById(R.id.button1);
        AppCompatButton button2 = findViewById(R.id.button2);
        AppCompatButton button3 = findViewById(R.id.button3);
        AppCompatButton buttonMinus = findViewById(R.id.buttonMinus);

        AppCompatButton button0 = findViewById(R.id.button0);
        AppCompatButton buttonOpenBracket = findViewById(R.id.buttonOpenBracket);
        AppCompatButton buttonCloseBracket = findViewById(R.id.buttonCloseBracket);
        AppCompatButton buttonPlus = findViewById(R.id.buttonPlus);

        AppCompatButton buttonClear = findViewById(R.id.buttonClear);
        AppCompatButton buttonDelete = findViewById(R.id.buttonDelete);
        AppCompatButton buttonEquals = findViewById(R.id.buttonEquals);

        AppCompatButton buttonLog = findViewById(R.id.buttonLog);
        AppCompatButton buttonLn = findViewById(R.id.buttonLn);
        AppCompatButton buttonDot = findViewById(R.id.buttonDot);
        AppCompatButton buttonPow = findViewById(R.id.buttonPow);

        AppCompatButton buttonSin = findViewById(R.id.buttonSin);
        AppCompatButton buttonCos = findViewById(R.id.buttonCos);
        AppCompatButton buttonTan = findViewById(R.id.buttonTan);

        //click listeners for the buttons
        powerButton.setOnClickListener(view -> {
            if(isCalculatorOn) {
                powerOffCalc();
            } else {
                powerOnCalc();
            }
        });

        button7.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(7);
        });

        button8.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(8);
        });

        button9.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(9);
        });

        buttonDiv.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("/");
        });

        button4.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(4);
        });

        button5.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(5);
        });

        button6.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(6);
        });

        buttonX.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("*");
        });


        button1.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(1);
        });

        button2.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(2);
        });

        button3.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(3);
        });

        buttonMinus.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("-");
        });


        button0.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDynamicNumber(0);
        });

        buttonOpenBracket.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("(");
        });

        buttonCloseBracket.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            //return if an opening counterpart, "(", is not provided
            if(bracketsBalance == 0) {
                return;
            }
            appendOperator(")");
        });

        buttonPlus.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("+");
        });


        buttonClear.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            //clear everything
            dynamicNumber = "";
            expressionList = new ArrayList<String>();
            bracketsBalance = 0;
            expressionScreen.setText("");
            answerScreen.setText("");
        });

        buttonDelete.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            //return if there's nothing
            if(expressionList.size() == 0 && dynamicNumber.length() == 0) {
                return;
            }
            if(dynamicNumber.length() == 0) {
                String lastOperator = expressionList.get(expressionList.size() - 1);
                int amount = lastOperator.length();
                expressionList.remove(expressionList.size() - 1);
                if(lastOperator.equals(")")) {
                    bracketsBalance += 1;
                } else if(lastOperator.equals("(")) {
                    bracketsBalance -= 1;
                }
                reduceExpressionScreen(amount);
                if(expressionList.size() > 0) {
                    if(isNumber(expressionList.get(expressionList.size() - 1))) {
                        dynamicNumber = expressionList.get(expressionList.size() - 1);
                        expressionList.remove(expressionList.size() - 1);
                    }
                }

            } else {
                dynamicNumber = dynamicNumber.substring(0, dynamicNumber.length() - 1);
                reduceExpressionScreen(1);
            }
        });

        buttonEquals.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            evaluate();
        });

        buttonLog.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("log");
            appendOperator("(");
        });
        buttonLog.setLongClickable(true);
        buttonLog.setOnLongClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return true;
            }
            appendOperator("alog");
            appendOperator("(");
            return true;
        });

        buttonLn.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("ln");
            appendOperator("(");
        });
        buttonLn.setLongClickable(true);
        buttonLn.setOnLongClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return true;
            }
            appendOperator("aln");
            appendOperator("(");
            return true;
        });

        buttonDot.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendDot();
        });

        buttonPow.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("^");
            appendOperator("(");
        });
        buttonPow.setLongClickable(true);
        buttonPow.setOnLongClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return true;
            }
            appendOperator("!");
            return true;
        });

        buttonSin.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("sin");
            appendOperator("(");
        });
        buttonSin.setLongClickable(true);
        buttonSin.setOnLongClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return true;
            }
            appendOperator("asin");
            appendOperator("(");
            return true;
        });

        buttonCos.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("cos");
            appendOperator("(");
        });
        buttonCos.setLongClickable(true);
        buttonCos.setOnLongClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return true;
            }
            appendOperator("acos");
            appendOperator("(");
            return true;
        });

        buttonTan.setOnClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return;
            }
            appendOperator("tan");
            appendOperator("(");
        });
        buttonTan.setLongClickable(true);
        buttonTan.setOnLongClickListener(view -> {
            //calculator must be on
            if(!isCalculatorOn) {
                return true;
            }
            appendOperator("atan");
            appendOperator("(");
            return true;
        });
    }
    //expressionList manipulation methods
    private void appendDynamicNumber(int number) {
        //clear expressionScreen if evaluation just occurred
        if(justEvaluated) {
            justEvaluated = false;
            expressionScreen.setText("");
            answerScreen.setText("");
        }
        if(dynamicNumber.length() == 0 || dynamicNumber.equals("0")) {
            dynamicNumber = String.valueOf(number);
        } else {
            dynamicNumber += String.valueOf(number);
        }
        expressionScreen.append(String.valueOf(number));
    }

    private void appendDot() {
        //clear expressionScreen if evaluation just occurred
        if(justEvaluated) {
            justEvaluated = false;
            expressionScreen.setText("");
            answerScreen.setText("");
        }
        //return if dynamicNumber already has dot
        if(dynamicNumber.indexOf(".") > 0) {
            return;
        }
        //set to zero if dynamic number is null
        if(dynamicNumber.length() == 0 || dynamicNumber.equals("-")) {
            dynamicNumber += "0.";
            expressionScreen.append("0.");
        } else {
            dynamicNumber += ".";
            expressionScreen.append(".");
        }
    }

    private void appendOperator(String operator) {
        if(Objects.equals(operator, "-")) {
            //clear expressionScreen if evaluation just occurred
            if(justEvaluated) {
                expressionScreen.setText("");
                expressionList.add(result);
                expressionScreen.append(result);
                expressionList.add("-");
                expressionScreen.append("-");
                answerScreen.setText("");
                justEvaluated = false;
                return;
            }
            //set the dynamic number if null
            if(dynamicNumber.length() == 0 && expressionList.size() == 0) {
                //if list is empty, checking the last element will cause an index out of bounds exception thus I check for emptiness first
                dynamicNumber = "-";
                expressionScreen.append("-");
            } else if(dynamicNumber.length() == 0 && !expressionList.get(expressionList.size() - 1).equals(")")) {
                dynamicNumber = "-";
                expressionScreen.append("-");
            } else if(!dynamicNumber.equals("-")) {
                //append the list
                if(dynamicNumber.length() > 0) {
                    expressionList.add(dynamicNumber);
                }
                expressionList.add("-");
                dynamicNumber = "";
                expressionScreen.append("-");
            }
        } else if(Objects.equals(operator, "+") || Objects.equals(operator, "/") || Objects.equals(operator, "*")) {
            //clear expressionScreen if evaluation just occurred
            if(justEvaluated) {
                expressionScreen.setText("");
                expressionList.add(result);
                expressionScreen.append(result);
                expressionList.add(operator);
                expressionScreen.append(operator);
                answerScreen.setText("");
                justEvaluated = false;
                return;
            }
            if((expressionList.size() == 0 && dynamicNumber.length() == 0) || dynamicNumber.equals("-")) {
                return;
            } else if(dynamicNumber.length() != 0 || Objects.equals(expressionList.get(expressionList.size() - 1), "!") || expressionList.get(expressionList.size() - 1) == ")") {
                if(dynamicNumber.length() > 0) {
                    expressionList.add(dynamicNumber);
                }
                expressionList.add(operator);
                dynamicNumber = "";
                if(operator.equals("*")) {
                    expressionScreen.append("X");
                } else if(operator.equals("/")) {
                    expressionScreen.append("÷");
                } else {
                    expressionScreen.append(operator);
                }
            }
        } else {
            //clear expressionScreen if evaluation just occurred
            if(justEvaluated) {
                justEvaluated = false;
                expressionScreen.setText("");
                answerScreen.setText("");
            }
            if(dynamicNumber.equals("-")) {
                return;
            }
            if(operator.equals(")")) {
                //don't close empty brackets
                if(dynamicNumber.length() == 0 && !(expressionList.get(expressionList.size() - 1).equals(")") || expressionList.get(expressionList.size() - 1).equals("!"))) {
                    return;
                }
                bracketsBalance -= 1;
            } else if(operator.equals("(")) {
                bracketsBalance += 1;
            }
            if(dynamicNumber.length() != 0) {
                expressionList.add(dynamicNumber);
                dynamicNumber = "";
            }
            expressionList.add(operator);
            expressionScreen.append(operator);
        }
    }

    //evaluation methods
    private String simplify(ArrayList<String> list) {
        //log
        while(list.contains("log")) {
            int position = list.lastIndexOf("log");
            list.set(position, String.valueOf(Math.log(Double.parseDouble(list.get(position + 1))) * LOG_CONVERSION_FACTOR));
            list.remove(position + 1);
        }
        //alog
        while(list.contains("alog")) {
            int position = list.lastIndexOf("alog");
            list.set(position, String.valueOf(Math.pow(10, Double.parseDouble(list.get(position + 1)))));
            list.remove(position + 1);
        }
        //ln
        while(list.contains("ln")) {
            int position = list.lastIndexOf("ln");
            list.set(position, String.valueOf(Math.log(Double.parseDouble(list.get(position + 1)))));
            list.remove(position + 1);
        }
        //aln
        while(list.contains("aln")) {
            int position = list.lastIndexOf("aln");
            list.set(position, String.valueOf(Math.exp(Double.parseDouble(list.get(position + 1)))));
            list.remove(position + 1);
        }
        //power
        while(list.indexOf("^") > 0) {
            int position = list.lastIndexOf("^");
            list.set(position - 1, String.valueOf(Math.pow(Double.parseDouble(list.get(position - 1)), Double.parseDouble(list.get(position + 1)))));
            list.remove(position);
        }
        //factorial
        while(list.indexOf("!") > 0) {
            int position = list.indexOf("!");
            list.set(position - 1, String.valueOf(factorial((int)Double.parseDouble(list.get(position - 1)))));
            list.remove(position);
        }
        //sin
        while(list.contains("sin")) {
            int position = list.lastIndexOf("sin");
            double operand = Double.parseDouble(list.get(position + 1));
            double simplifiedOperand = operand % 360;
            //special cases
            if(simplifiedOperand == 0 || simplifiedOperand == 180) {
                list.set(position, "0");
            } else if(simplifiedOperand == 90 || simplifiedOperand == -270) {
                list.set(position, "1");
            } else if(simplifiedOperand == 270 || simplifiedOperand == -90) {
                list.set(position, "-1");
            } else {
                list.set(position, String.valueOf(Math.sin(operand * Math.PI / 180)));
            }
            list.remove(position + 1);
        }
        //asin
        while(list.contains("asin")) {
            int position = list.lastIndexOf("asin");
            double operand = Double.parseDouble(list.get(position + 1));
            //special cases
            if(operand == 0) {
                list.set(position, "0");
            } else if(operand == 1) {
                list.set(position, "90");
            } else if(operand == -1) {
                list.set(position, "270");
            } else {
                list.set(position, String.valueOf(Math.asin(operand) * 180 / Math.PI));
            }
            list.remove(position + 1);
        }
        //cos
        while(list.contains("cos")) {
            int position = list.lastIndexOf("cos");
            double operand = Double.parseDouble(list.get(position + 1));
            double simplifiedOperand = operand % 360;
            //special cases
            if(operand % 360 == 0) {
                list.set(position, "1");
            } else if(simplifiedOperand == 90 || simplifiedOperand == 270 || simplifiedOperand == -90 || simplifiedOperand == -270) {
                list.set(position, "0");
            } else if(simplifiedOperand == 180) {
                list.set(position, "-1");
            } else {
                list.set(position, String.valueOf(Math.cos(operand * Math.PI / 180)));
            }
            list.remove(position + 1);
        }
        //acos
        while(list.contains("acos")) {
            int position = list.lastIndexOf("acos");
            double operand = Double.parseDouble(list.get(position + 1));
            //special cases
            if(operand == 0) {
                list.set(position, "90");
            } else if(operand == 1) {
                list.set(position, "0");
            } else if(operand == -1) {
                list.set(position, "180");
            } else {
                list.set(position, String.valueOf(Math.acos(operand) * 180 / Math.PI));
            }
            list.remove(position + 1);
        }
        //tan
        while(list.contains("tan")) {
            int position = list.lastIndexOf("tan");
            double operand = Double.parseDouble(list.get(position + 1));
            double simplifiedOperand = operand % 360;
            //special cases
            if(simplifiedOperand == 0 || simplifiedOperand == 180) {
                list.set(position, "0");
            } else if(simplifiedOperand == 90 || simplifiedOperand == -270) {
                list.set(position, String.valueOf(Double.POSITIVE_INFINITY));
            } else if(simplifiedOperand == 270 || simplifiedOperand == -90) {
                list.set(position, String.valueOf(Double.NEGATIVE_INFINITY));
            } else if(simplifiedOperand == 45 || simplifiedOperand == 225) {
                list.set(position, "1");
            } else if(simplifiedOperand == 135 || simplifiedOperand == 315 || simplifiedOperand == -45) {
                list.set(position, "-1");
            } else {
                list.set(position, String.valueOf(Math.tan(operand * Math.PI / 180)));
            }
            list.remove(position + 1);
        }
        //atan
        while(list.contains("atan")) {
            int position = list.lastIndexOf("atan");
            double operand = Double.parseDouble(list.get(position + 1));
            //special cases
            if(operand == 0) {
                list.set(position, "0");
            } else if(operand == 1) {
                list.set(position, "45");
            } else if(operand == -1) {
                list.set(position, "135");
            } else if(operand == Double.POSITIVE_INFINITY) {
                list.set(position, "90");
            } else if(operand == Double.NEGATIVE_INFINITY) {
                list.set(position, "270");
            } else {
                list.set(position, String.valueOf(Math.atan(operand) * 180 / Math.PI));
            }
            list.remove(position + 1);
        }

        //division
        while(list.indexOf("/") >= 0) {
            int position = list.indexOf("/");
            list.set(position - 1, String.valueOf(Double.parseDouble(list.get(position - 1)) / Double.parseDouble(list.get(position + 1))));
            list.remove(position);
            list.remove(position);
        }
        //multiplication
        while(list.contains("*")) {
            int position = list.indexOf("*");
            list.set(position - 1, String.valueOf(Double.parseDouble(list.get(position - 1)) * Double.parseDouble(list.get(position + 1))));
            list.remove(position);
            list.remove(position);
        }
        //subtraction
        while(list.contains("-")) {
            int position = list.indexOf("-");
            list.set(position - 1, String.valueOf(Double.parseDouble(list.get(position - 1)) - Double.parseDouble(list.get(position + 1))));
            list.remove(position);
            list.remove(position);
        }
        //addition
        while(list.contains("+")) {
            int position = list.indexOf("+");
            list.set(position - 1, String.valueOf(Double.parseDouble(list.get(position - 1)) + Double.parseDouble(list.get(position + 1))));
            list.remove(position);
            list.remove(position);
        }

        //implicit multiplication
        while(list.size() > 1) {
            list.set(0, String.valueOf(Double.parseDouble(list.get(0)) * Double.parseDouble(list.get(1))));
            list.remove(1);
        }

        return list.get(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void evaluate() {
        //input for devMode -> negative nine zeros and five fives
        //-00000000055555
        if(expressionList.size() == 0 && dynamicNumber.equals("-00000000055555")) {
            //display the error and stacktrace
            File rootApplicationFolder = getApplicationContext().getFilesDir();
            String error = "NO ERRORS OCCURRED YET!";
            try {
                error = new String(Files.readAllBytes(new File(rootApplicationFolder, "ERROR_LOG_FOR_anville95.maven").toPath()));
                justEvaluated = true;
                expressionScreen.setText(error.substring(0, error.indexOf("::::")));
                answerScreen.setText(error.substring(error.indexOf("::::") + 4));
            } catch (Exception excep) {
                Toast.makeText(this, "Couldn't Load Errors", Toast.LENGTH_SHORT).show();
                justEvaluated = true;
                expressionScreen.setText(error);
                answerScreen.setText(error);
                //reset the list
                expressionList = new ArrayList<String>();
                bracketsBalance = 0;
            }
            return;
        }
        try{
            //return if things are empty or if brackets are not all closed
            if((expressionList.size() == 0 && dynamicNumber.length() == 0) || bracketsBalance != 0) {
                if(bracketsBalance > 0) {
                    Toast.makeText(this, "Too less closing brackets", Toast.LENGTH_SHORT).show();
                }
                return;
            } else if(expressionList.size() > 0 && dynamicNumber.length() == 0) {
                String lastElement = expressionList.get(expressionList.size() - 1);
                if(!(lastElement.equals("!") || lastElement.equals(")"))) {
                    return;
                }
            }
            //add dynamicNumber's content if available
            if(dynamicNumber.length() > 0) {
                expressionList.add(dynamicNumber);
                dynamicNumber = "";
            }
            //brackets
            while(expressionList.contains("(")) {
                int startIndex = expressionList.lastIndexOf("(") + 1;
                int stopIndex = expressionList.subList(startIndex, expressionList.size()).indexOf(")") + startIndex;
                ArrayList<String> sublist = new ArrayList<String>(expressionList.subList(startIndex, stopIndex));
                expressionList.set(startIndex - 1, simplify(sublist));
                //remove items from (startIndex + 1) to (stopIndex)
                for(int i = startIndex; i <= stopIndex; i++) {
                    expressionList.remove(startIndex);
                }
            }
            //now just simplify the bracket-less expressionList
            result = simplify(expressionList);
            justEvaluated = true;
            answerScreen.setText(result);
            //reset the list
            expressionList = new ArrayList<String>();
            bracketsBalance = 0;
        } catch (Exception e) {
            File rootApplicationFolder = getApplicationContext().getFilesDir();
            if(!(new File(rootApplicationFolder, "ERROR_LOG_FOR_anville95.maven").exists())) {
                try {
                    Files.createFile(new File(rootApplicationFolder, "ERROR_LOG_FOR_anville95.maven").toPath());
                } catch (Exception exception) {
                    Toast.makeText(this, "Failed to log error, please grant file access permission.", Toast.LENGTH_SHORT).show();
                }
            }

            //write the file
            try{
                Files.write(new File(rootApplicationFolder, "ERROR_LOG_FOR_anville95.maven").toPath(), ("expression {" + expressionList.toString() + "}, error ->" + e + "::::" + Arrays.toString(e.getStackTrace())).getBytes(StandardCharsets.UTF_8));
            } catch (Exception exp) {
                Toast.makeText(this, "Couldn't write error to file", Toast.LENGTH_SHORT).show();
            }
            justEvaluated = true;
            answerScreen.setText("sorry, an error occurred");
            //reset the list
            expressionList = new ArrayList<String>();
            bracketsBalance = 0;
        }
    }

    private int factorial(int num) {
        if(num == 0 || num == 1) {
            return 1;
        }
        return num * factorial(num - 1);
    }

    private boolean isNumber(String text) {
        try {
            Double number = Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void reduceExpressionScreen(int amount) {
        String initialExpressionText = expressionScreen.getText().toString();
        expressionScreen.setText(initialExpressionText.substring(0, initialExpressionText.length() - amount));
    }

    private void powerOnCalc() {
        if(isBooting) {
            return;
        }
        isBooting = true;
        expressionScreen.setBackgroundResource(R.drawable.screen_background);
        expressionScreen.setText(R.string.power_on_calc_expression_screen_text);
        answerScreen.setBackgroundResource(R.drawable.screen_background);
        answerScreen.setText(R.string.power_on_calc_answer_screen_text);
        powerButton.setBackgroundResource(R.drawable.power_button_background);

        new Thread(new Runnable() {
            @Override
            public void run() {
                long duration = 1200;//milliseconds

                long elapsedTime = 0;

                long initialTime = System.currentTimeMillis();

                long twentieth = duration / 20;

                int currentTwentieth = 0;

                while(elapsedTime <= duration) {
                    elapsedTime = System.currentTimeMillis() - initialTime;
                    if((int)(elapsedTime / twentieth) > currentTwentieth) {
                        currentTwentieth = (int)(elapsedTime / twentieth);
                        answerScreen.setText(bootTextsArray[currentTwentieth - 1]);
                    }
                }

                //booting complete
                powerButton.setText(R.string.powerButtonTextOn);
                expressionScreen.setText("");
                answerScreen.setText("");
                isCalculatorOn = true;
                isBooting = false;
            }
        }){}.start();
    }

    private void powerOffCalc() {
        if(isBooting) {
            return;
        }
        isBooting = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                expressionScreen.setText(R.string.power_on_calc_expression_screen_text);
                answerScreen.setText(R.string.power_on_calc_answer_screen_text_off);

                long duration = 500;//milliseconds

                long elapsedTime = 0;

                long initialTime = System.currentTimeMillis();

                while(elapsedTime <= duration) {
                    elapsedTime = System.currentTimeMillis() - initialTime;
                }

                //booting complete
                expressionScreen.setBackgroundResource(R.drawable.screen_background_off);
                answerScreen.setBackgroundResource(R.drawable.screen_background_off);
                powerButton.setBackgroundResource(R.drawable.power_button_background_off);
                powerButton.setText(R.string.powerButtonTextOff);
                expressionScreen.setText("");
                answerScreen.setText("");
                //clear everything
                dynamicNumber = "";
                expressionList = new ArrayList<String>();
                bracketsBalance = 0;
                isCalculatorOn = false;
                isBooting = false;
            }
        }){}.start();
    }
}