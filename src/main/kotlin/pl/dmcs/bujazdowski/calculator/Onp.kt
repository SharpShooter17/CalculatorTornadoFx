package pl.dmcs.bujazdowski.calculator

import org.apache.commons.lang3.StringUtils
import java.util.*
import kotlin.math.pow

object Onp {

    private val priority: Map<String, Int> = mapOf(
        "^" to 4,
        "*" to 3,
        "/" to 3,
        "+" to 2,
        "-" to 2,
        "=" to 1,
        "(" to 0
    )

    private val operands = priority.keys + ")"

    fun convert(input: String): List<String> {
        val tokens = tokenize(input)

        val stack = Stack<String>()
        val result = mutableListOf<String>()

        tokens.forEach { token ->
            when (token) {
                in "^", "*", "/", "+", "-" -> {
                    while (
                        !stack.empty() &&
                        (priority.get(stack.lastElement())?.compareTo(priority.get(token) ?: 10) ?: 1) >= 0
                    ) {
                        result.add(stack.pop())
                    }
                    stack.push(token)
                }
                "(" -> stack.push(token)
                ")" -> {
                    var top = stack.pop()
                    while (top != "(") {
                        result.add(top)
                        top = stack.pop()
                    }
                }
                else -> result.add(token)
            }
        }

        while (!stack.empty()) {
            result.add(stack.pop())
        }

        return result
    }

    fun evaluate(onp: List<String>): Double {
        val onpWithoutEquals = onp.filter { it != "=" }
        if (onpWithoutEquals.size <= 2) return 0.0

        val stack = Stack<Double>()

        onpWithoutEquals.forEach { token ->
            if (StringUtils.isNumeric(token)) {
                stack.push(token.toDouble())
            } else {
                val op2 = stack.pop()
                val op1 = stack.pop()
                val result = when (token) {
                    "+" -> op1 + op2
                    "-" -> op1 - op2
                    "*" -> op1 * op2
                    "/" -> op1 / op2
                    "^" -> op1.pow(op2)
                    else -> throw IllegalStateException()
                }
                stack.push(result)
            }
        }

        return stack.pop()
    }

    private fun tokenize(input: String): List<String> {
        val tokens = mutableListOf<String>()
        val builder = StringBuilder()

        input.forEach { char ->
            if (operands.contains(char.toString())) {
                tokens.add(builder.toString())
                tokens.add(char.toString())
                builder.clear()
            } else {
                builder.append(char)
            }
        }

        tokens.add(builder.toString())
        return tokens.filter { it.isNotEmpty() }
    }

    fun isOperand(x: String): Boolean {
        return (operands - "=").contains(x)
    }


}