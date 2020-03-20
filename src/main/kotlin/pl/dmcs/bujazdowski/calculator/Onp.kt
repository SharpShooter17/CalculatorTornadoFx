package pl.dmcs.bujazdowski.calculator

import java.util.*

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


}