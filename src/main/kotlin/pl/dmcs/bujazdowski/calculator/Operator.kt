package pl.dmcs.bujazdowski.calculator

sealed class Operator(val x: Long) {

    abstract fun calculate(y: Long): Long

    class add(x: Long) : Operator(x) {
        override fun calculate(y: Long): Long {
            return x + y
        }
    }

    class substract(x: Long) : Operator(x) {
        override fun calculate(y: Long): Long {
            return x - y
        }
    }

    class multiply(x: Long) : Operator(x) {
        override fun calculate(y: Long): Long {
            return x * y
        }
    }

    class divide(x: Long) : Operator(x) {
        override fun calculate(y: Long): Long {
            return x / y
        }
    }

}