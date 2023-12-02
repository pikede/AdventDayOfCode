fun main() {

/*

//    findSmallest(intArrayOf(1, 0, 9, 5, 2, 4), 3).forEach {
//        print("$it, ")
//    }

*/
/*    println(
        getLongestWord(
            listOf(
                "cat",
                "banana",
                "dog",
                "nana",
                "walk",
                "walker",
                "dogwalker",
                "walkcatnana"
            )
        )
    )   // walkcatnana
    println(
        getLongestWord(
            listOf(
                "cat",
                "banana",
                "dog",
                "nana",
                "walks",
                "walker",
                "dogwalker",
                "walkcatnana"
            )
        )
    )  // dogwalker
    println(
        getLongestWord(
            listOf(
                "cat",
                "banana",
                "dog",
                "nana",
                "walk",
                "walker",
                "dogwalker",
                "walkCatnana"
            )
        )
    )   // walkCatnana
    println(
        getLongestWord(
            listOf(
                "cat",
                "banana",
                "dog",
                "nana",
                "",
                "walk",
                "walker",
                "dogwalker",
                "walkCatnana"
            )
        )
    )   // walkCatnana*//*
*/
/*

//    println(getMaxAlternatingSum(listOf(30, 15, 60, 75, 45, 15, 15, 45)))  // 180
//    println(getMaxAlternatingSum(listOf(30, 15, 60, 75, 15, 45, 15, 45)))  // 195
//    println(getMaxAlternatingSum(listOf(75, 105, 120, 75, 90, 135)))  //  330
//    println(getMaxAlternatingSum(listOf(30, 15, 15, 45)))  // 75
//    println(getMaxAlternatingSum(listOf(45, 60, 45, 15)))  // 90

//    println(cntSteps(intArrayOf(1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9)))
//    println(multiSearch("mississippi", arrayOf("is", "ppi", "hi", "sis", "i", "ssippi")))


   for (i in shortestSuperSequenceVersion1(
        intArrayOf(1,5, 9),
        intArrayOf(7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7)
    )) {
        print("$i, ")
    }

    println()
    for (i in shortestSuperSequenceVersion2(
        intArrayOf(1, 5, 9),
        intArrayOf(7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7)
    )) {
        print("$i, ")
    }
    println()


    for (i in shortestSuperSequenceVersion1(
        intArrayOf(1,5, 8),
        intArrayOf(7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7)
    )) {
        print("$i, ")
    }

    println()
    for (i in shortestSuperSequenceVersion2(
        intArrayOf(1, 5, 8),
        intArrayOf(7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7)
    )) {
        print("$i, ")
    }

//    println(getMissingNumber(intArrayOf(2, 1, 4, 3, 6, 7)))
//    println(getMissingNumber2(intArrayOf(1, 4, 6, 7, 5,9,10,11,15,13,14, 2, 12)))  // 8,3
//    println(lastRemaining(9))
    println((Math.pow(3.0, (10 - 2).toDouble() / 3) * 2).toInt())

    *//*
*/

/*

    println(
        buildOrder(
            arrayListOf("a", "b", "c", "d", "e", "f", "g"),
            dependencies = arrayListOf(
                Dependent("d", "g"),
                Dependent("f", "b"),
                Dependent("f", "c"),
                Dependent("f", "a"),
                Dependent("a", "e"),
                Dependent("b", "e"),
                Dependent("b", "a"),
                Dependent("c", "a")
            )
        )
    )

    println(getNumWays(100))

    var median = Median()
    println(median.getMedian(add(1, 10)))
    println(median.getMedian(add(1, 10)))
    println(median.getMedian(add(1, 10)))
    println(median.getMedian(add(1, 10)))
    println(median.getMedian(add(1, 10)))
    println(median.getMedian(add(1, 10)))

*/


//    println(findVolumeOfHistogram(intArrayOf(0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0)))

    println(
        getMatchingWords(
            "DAMP",
            "LIKE",
            listOf("LIKE", "EKIL", "KEN", "BLANKA", "KENTUCKY", "DAMP", "LAMP", "LIMP", "LIME")
        )
    )
}


fun getMatchingWords(first: String, last: String, dictionary: List<String>): ArrayList<String> {
    var ans = ArrayList<String>()
    var memo = HashMap<String, Boolean>()
//    memo[first] = true
//    return if (getMatch(first, last, dictionary.toHashSet(), ans, hashSetOf(first), memo)) ans else arrayListOf()
    return if (getMatch2(
            first,
            last,
            dictionary.toHashSet(),
            ans,
            hashSetOf(first),
            memo,
            getWordPatterns(dictionary)
        )
    ) ans else arrayListOf()
}

fun getMatch2(
    currentWord: String,
    last: String,
    dictionary: HashSet<String>,
    ans: ArrayList<String>,
    level: HashSet<String>,
    memo: HashMap<String, Boolean>,
    map: HashMap<String, HashSet<String>>
): Boolean {

    if (currentWord.equals(last, true)) {
//        memo[currentWord] = true
        ans.addAll(level)
        return true
    }

//    if (memo[currentWord] == true) {
//        return true
//    }


    var matches = false
    for (i in currentWord.indices) { // m -> length of string
        if (i + 1 > currentWord.length) {
            break
        }
        var left = currentWord.substring(0, i).toLowerCase()
        var right = currentWord.substring(i + 1).toLowerCase()
        var total = left + "_" + right

        var wordPatterns = map[total] ?: hashSetOf()
            println(wordPatterns.size)
        for (element in wordPatterns) {  // k -> string possibilities
            if (level.contains(element).not()) {
                level.add(element)
                matches = matches || getMatch2(element, last, dictionary, ans, level, memo, map)  // 2 ^ k
                level.remove(element)
            }
        }
    }

//    memo[currentWord] = matches
    return matches
}

fun getMatch(
    currentWord: String,
    last: String,
    dictionary: HashSet<String>,
    ans: ArrayList<String>,
    level: HashSet<String>,
    memo: HashMap<String, Boolean>
): Boolean {

    if (currentWord.equals(last, true)) {
        memo[currentWord] = true
        ans.addAll(level)
        return true
    }

    if (memo[currentWord] == true) {
        return true
    }

    var matches = false
    for (i in currentWord.indices) {
        if (i + 1 > currentWord.length) {
            break
        }
        var left = currentWord.substring(0, i).toLowerCase()
        var right = currentWord.substring(i + 1).toLowerCase()

        for (k in dictionary) {
            var element = k.toLowerCase()
            if (element.contains(left)
                && element.contains(right)
                && currentWord.toLowerCase() != element
                && (level.contains(element).not() && level.contains(element.toUpperCase()).not())
            ) {
                level.add(k)
                matches = matches || getMatch(k, last, dictionary, ans, level, memo)
                level.remove(k)
            }
        }
    }

    memo[currentWord] = matches
    return matches
}

fun getWordPatterns(dictionary: List<String>): HashMap<String, HashSet<String>> {
    var map = HashMap<String, HashSet<String>>()

    for (currentWord in dictionary) {
        for (i in currentWord.indices) {
            var left = currentWord.substring(0, i).toLowerCase()
            var right = currentWord.substring(i + 1).toLowerCase()
            var total = left + "_" + right
            if (map.contains(total)) {
                map[total]?.add(currentWord)
                continue
            }
            map[total] = hashSetOf(currentWord)
        }
    }
    return map
}

fun findTallestBarIndex(height: IntArray, start: Int, end: Int): Int {
    var max = start
    for (i in start + 1..end) {
        if (height[i] > height[max]) {
            max = i
        }
    }
    return max
}

fun findVolumeOfHistogram(height: IntArray): Int {
/*    var left = 0
    var right = height.lastIndex
    var leftMax = 0
    var rightMax = 0
    var volume = 0

    while (left < right) {
        if (height[left] < height[right]) {
            if (leftMax <= height[left]) {
                leftMax = height[left]
            } else {
                volume += leftMax - height[left]
            }
            left++
        } else {
            if (rightMax <= height[right]) {
                rightMax = height[right]
            } else {
                volume += rightMax - height[right]
            }
            right--
        }
    }


    return volume*/
    var start = 0
    var end = height.lastIndex
    var max = findTallestBarIndex(height, start, end)
    var leftVolume = getTotalVolume(height, start, max, true)
    var rightVolume = getTotalVolume(height, max, end, false)

    return leftVolume + rightVolume
}

fun getTotalVolume(height: IntArray, start: Int, end: Int, isLeft: Boolean): Int {
    if (start >= end) {
        return 0
    }

    var sum = 0


    if (isLeft) {
        var max = findTallestBarIndex(height, start, end - 1)
        sum += borderVolume(height, max, end)
        sum += getTotalVolume(height, start, max, isLeft)
    } else {
        var max = findTallestBarIndex(height, start + 1, end)
        sum += borderVolume(height, start, max)
        sum += getTotalVolume(height, max, end, isLeft)
    }

    return sum
}

fun borderVolume(height: IntArray, start: Int, end: Int): Int {
    var min = minOf(height[start], height[end])
    var sum = 0
    for (i in start + 1 until end) {
        sum += min - height[i]
    }

    return sum
}


/*
fun IntArray.maxBarIndex(start: Int, end: Int): Int {
    var max = start
    for (i in start + 1..end) {
        if (this[i] > this[max]) {
            max = i
        }
    }

    return max
}


fun findVolumeOfHistogram(height: IntArray): Int {
//    var start = 0
//    var end = height.lastIndex
//
//    var maxBarIndex = height.maxBarIndex(start, end)
//    var leftVolume = getVolume(height, start, maxBarIndex, true)
//    var rightVolume = getVolume(height, maxBarIndex, end, false)
//
//    return leftVolume + rightVolume

    return precomputeMax(height)
}

fun precomputeMax(height: IntArray): Int {
    var leftMax = IntArray(height.size)
    var maxRight = IntArray(height.size)
    leftMax[0] = height[0]
    maxRight[maxRight.lastIndex] = height[height.lastIndex]
    for (i in 1 until height.size) {
        leftMax[i] = maxOf(height[i], leftMax[i - 1])
    }
    var sum = 0
    var rightMax = height[height.lastIndex]
    for (i in height.lastIndex downTo 0) {
        rightMax = maxOf(height[i], rightMax)
        var secondTallest = minOf(rightMax, leftMax[i])

        sum += maxOf(secondTallest - height[i], 0 )
//        if (secondTallest > height[i]) {
//            sum += secondTallest - height[i]
//        }

    }
//    for (i in height.size - 2 downTo 0) {
//        maxRight[i] = maxOf(height[i], maxRight[i + 1])
//    }
//    for (i in maxLeft.indices) {
//        sum += minOf(maxLeft[i], maxRight[i]) - height[i]
//    }

    return sum

}

fun getVolume(height: IntArray, start: Int, end: Int, isLeft: Boolean): Int {
    if (start >= end) {
        return 0
    }
    var sum = 0
    if (isLeft) {
        var max = height.maxBarIndex(start, end - 1)
        sum += borderVolume(height, max, end)
        sum += getVolume(height, start, max, isLeft)
    } else {
        var max = height.maxBarIndex(start + 1, end)
        sum += borderVolume(height, start, max)
        sum += getVolume(height, max, end, isLeft)
    }

    return sum
}

fun borderVolume(height: IntArray, start: Int, end: Int): Int {
    if (start >= end) {
        return 0
    }

    var min = minOf(height[end], height[start])
    var volume = 0
    for (i in start + 1 until end) {
        volume += min - height[i]
    }
    return volume
}

fun add(low: Int, high: Int) = low + Random().nextInt(high - low + 1)

class Median {
    var minHeap = PriorityQueue<Int>()
    var maxHeap = PriorityQueue<Int> { a, b -> b - a }


    // pre condition high heap has more elements than max heap
    fun getMedian(x: Int): Double {
        if (maxHeap.size == minHeap.size) {
            if (minHeap.isNotEmpty() && x > minHeap.peek()) {
                maxHeap.offer(minHeap.poll())
                minHeap.offer(x)
            } else {
                maxHeap.offer(x)
            }
        } else if (x < maxHeap.peek()) {
            minHeap.offer(maxHeap.poll())
            maxHeap.offer(x)
        } else {
            minHeap.offer(x)
        }

        println("low:: $minHeap      -->  high $maxHeap")

        return if (minHeap.size == maxHeap.size) {
            (minHeap.peek().toDouble() + maxHeap.peek().toDouble()) / 2
        } else {
            maxHeap.peek().toDouble()
        }
    }

}

fun getNumWays(n: Int): Int {
    var coins = intArrayOf(25, 10, 5, 1)
    return cntWays(n, coins, 0)
}

fun cntWays(amount: Int, coins: IntArray, start: Int): Int {
    if (start >= coins.size - 1) return 1
    var coinValue = coins[start]

    var i = 0
    var numWays = 0
    while (i * coinValue <= amount) {
        numWays += cntWays(amount - (i * coinValue), coins, start + 1)
        i++
    }

    return numWays
}


class Dependent(val req: String, val course: String)

fun buildOrder(
    projects: ArrayList<String>,
    dependencies: ArrayList<Dependent>
): ArrayList<String> {
    var map = HashMap<String, HashSet<String>>()


    for (i in dependencies) {
        var temp = map[i.course] ?: HashSet<String>()
        temp.add(i.req)
        map[i.course] = temp
    }

//    var q: Queue<String> = PriorityQueue<String>()
//    for (i in projects) {
//        if (map.contains(i).not()) {
//            q.offer(i) // adds e and f
//        }
//    }
//
//    var ans = ArrayList<String>()
//    while (q.isNotEmpty()) {
//        var curr = q.poll()
//        ans.add(curr)
//        var temp = HashSet<String>()
//        for ((k, v) in map) {
//            if (v.contains(curr)) {
//                v.remove(curr)
//                if (v.isEmpty()) {
//                    q.offer(k)
//                    temp.add(k)
//                }
//            }
//        }
//        temp.forEach { map.remove(it) }
//    }

    var q = ArrayList<String>()

    for (i in projects.reversed()) {
        if (map.contains(i).not()) {
            q.add(i) // adds e and f
        }
    }

    var ans = ArrayList<String>()
    while (q.isNotEmpty()) {
        var curr = q.removeAt(0)
        ans.add(curr)
        var temp = HashSet<String>()
        for ((k, v) in map) {
            if (v.contains(curr)) {
                v.remove(curr)
                if (v.isEmpty()) {
                    q.add(k)
                    temp.add(k)
                }
            }
        }
        temp.forEach { map.remove(it) }
    }

    return ans
}


fun lastRemaining(n: Int): Int {
    if (n == 1) return 1
    var half = n / 2
    var nextLayer = lastRemaining(half)

    return (half - nextLayer + 1) * 2
}

fun getMissingNumber(arr: IntArray): Int {
    if (arr.isEmpty()) {
        return -1
    }

    /* var set = HashSet<Int>()

     for (i in 1..arr.size) {
         set.add(i)
     }

     for (i in arr) {
         if (set.contains(i)) {
             set.remove(i)
         }
     }

     println(set)

     return if (set.isNotEmpty()) set.first() else -1*/

    var max = arr.size + 1
    var currSum = arr.sum()

    var totalSum = 0
    for (i in 1..max) {
        totalSum += i
    }

    return totalSum - currSum
}  // N| 1

fun getMissingNumber2(arr: IntArray): Pair<Int, Int> {
    var currSum = arr.sum()
    var end = arr.size + 2

    var totalSum = 0
    for (i in 1..end) {
        totalSum += i
    }

    var missing = totalSum - currSum

    var start = 1

    while (start < end) {
        var sum = start + end
        when {
            sum == missing && !arr.contains(start) && !arr.contains(end) -> return start to end
            sum > missing -> end--
            else -> start++
        }
    }

    return -1 to -1
} // N | 1

/*

//edge cases empty small,
//edge cases empty big,
//edge small doesn't exist in big,
// every small and big are equal
// big is less than small
fun shortestSuperSequenceVersion1(small: IntArray, big: IntArray): IntArray {
    var smallSet = small.toSet()
    var subArray = IntArray(2) { 0 }
    subArray[1] = Int.MAX_VALUE
    for (i in big.indices) {  // n
        var currentSet = HashSet<Int>()
        currentSet.add(big[i])
        for (j in i + 1..big.lastIndex) {//  n
            currentSet.add(big[j])
            var start = subArray[0]
            var end = subArray[1]
            var distance = end - start
            if (currentSet.containsAll(smallSet) && distance > j - i) {
                subArray[0] = i
                subArray[1] = j
            }
        }
    }

    return subArray
} // Time N^2 | Space N + M

fun shortestSuperSequenceVersion2(small: IntArray, big: IntArray): IntArray {
    var smallSet = small.toSet()
    var subArray = IntArray(2) { 0 }
    subArray[1] = Int.MAX_VALUE

    var start = 0
    var end = 0
    var currentSet = ArrayList<Int>()

    while (end < big.size) {
        currentSet.add(big[end])

        var i = subArray[0]
        var j = subArray[1]
        var distance = j - i

        while (currentSet.containsAll(smallSet) && start <= end) {
            if (distance > end - start) {
                subArray[0] = start
                subArray[1] = end
            }
            currentSet.removeAt(0)
            start++
        }

        end++
    }

    return subArray
} // N*m |  N

fun multiSearch(b: String, T: Array<String>): HashMap<String, ArrayList<Int>> {
    var map = HashMap<String, ArrayList<Int>>()
    for (element in T) { // N
        for (i in b.indices) {  // M
            var length = element.length
            if (i + length > b.length) continue
            var sub = b.substring(i, i + length)
            if (sub.contains(element)) {
                var temp = map[element] ?: ArrayList<Int>()
                temp.add(i)
                map[element] = temp
            }
        }
    } // N *M | N

    return map
}


fun cntSteps(steps: IntArray): Int {
    return getMinSteps(steps, 0, steps.lastIndex)
}

fun getMinSteps(steps: IntArray, start: Int, end: Int): Int {
    if (start == end) {
        return 0
    }

    if (steps[start] == 0) {
        return Int.MAX_VALUE
    }

    var min = Int.MAX_VALUE
    var i = start + 1
    while (i <= end && i <= start + steps[start]) {
        var jump = getMinSteps(steps, i, end)
        if (jump != Int.MAX_VALUE && jump + 1 < min) {
            min = jump + 1
        }
        i++
    }

    return min
}

fun getMaxAlternatingSum(input: List<Int>): Int {
//    var dp = IntArray(input.size + 2)
//
//    for (i in input.size - 1 downTo 0) {  // dp [i+2] +  original[i]  vs dp [ i + 1]  -> take maximum
//        var with = dp[i + 2] + input[i]
//        var without = dp[i + 1]
//        dp[i] = maxOf(with, without)
//    }
//
//    return dp[0]

//    var oneAway = 0
//    var twoAway = 0
//
//    for (i in input.size - 1 downTo 0) {  // twoAway +  original[i]  vs oneAway  -> current is max
//        var with = input[i] + twoAway
//        var without = oneAway
//
//        var current  = maxOf(with, without)
//        twoAway = oneAway
//        oneAway = current
//    }
//
//    return oneAway

    var oneAway = 0
    var twoAway = 0

    for (element in input) {  // twoAway +  original[i]  vs oneAway  -> current is max
        var with = element + twoAway
        var without = oneAway

        var current = maxOf(with, without)
        twoAway = oneAway
        oneAway = current
    }

    return oneAway

//    var ans = ArrayList<Int>()
//    var memo = IntArray(input.size)
//    backtrack(ans, input, ArrayList<Int>(), 0, memo)
//
//    return ans.sum()

//    return getMaxMinutes(input, 0)
//    var memo = IntArray(input.size)
//    getMaxMinutes(input, 0, memo)

//    return memo[0]
}

//fun getMaxMinutes(input: List<Int>, start: Int): Int {
//    if (start >= input.size) {
//        return 0
//    }
//
//    var withoutNextReservation = input[start] + getMaxMinutes(input, start + 2)
//    var includeNextReservation = getMaxMinutes(input, start + 1)
//
//    return maxOf(withoutNextReservation, includeNextReservation)
//}


fun getMaxMinutes(input: List<Int>, start: Int, memo: IntArray): Int {
    if (start >= input.size) {
        return 0
    }

    if (memo[start] == 0) {
        var withoutNextReservation = input[start] + getMaxMinutes(input, start + 2, memo)
        var includeNextReservation = getMaxMinutes(input, start + 1, memo)
        memo[start] = maxOf(withoutNextReservation, includeNextReservation)
    }

    return memo[start]
}


fun backtrack(ans: ArrayList<Int>, input: List<Int>, curr: ArrayList<Int>, start: Int, memo: IntArray) {
    if (ans.sum() < curr.sum()) {
        ans.clear()
        ans.addAll(curr)
    }

    if (start >= memo.size) {
        return
    }

    for (i in start until input.size) {  // n
        curr.add(input[i])
        backtrack(ans, input, curr, i + 2, memo)  //
        curr.removeAt(curr.lastIndex)
    }

    memo[start] = maxOf(curr.sum(), memo[start])
}

fun getLongestWord(input: List<String>): String {
    var set = input.toSet()

    var ans = ""
    for (i in set) {  // n
        if (isBuildable(set, i, true) && i.length > ans.length) {
            ans = i
        }
    }
    return ans

}

fun isBuildable(set: Set<String>, word: String, firstTimer: Boolean): Boolean {
    if (set.any { it.equals(word, true) } && !firstTimer) {
        return true
    }

    for (i in word.indices) {  // m
        var left = word.substring(0, i)
        var right = word.substring(i)
        var contains = set.any { it.equals(left, true) }
        if (contains && isBuildable(set, right, false)) {  // n!
            return true
        }
    }

    return false
}


/*fun canBuildWord(str: String, isOriginal: Boolean, map: HashMap<String, Boolean>): Boolean {
    if (map.contains(str) && !isOriginal) {
        return map[str] ?: false
    }

    for (i in str.indices) { // s
        var left = str.substring(0, i)
        p doesn;''

        if (map.contains(left) && map[left] == true && canBuildWord(right, false, map)) { //
            return true
        }
    }
    map[str] = false
    return false
}

fun sortByLength(input: List<String>): Array<String> {
    return input.sortedDescending().toTypedArray()
}*/


fun findSmallest(arr: IntArray, k: Int): IntArray {
    if (arr.size < k || k <= 0) {
        throw Exception("kjsdo")
    }  // edge case

    var threshold = rank(arr, k - 1)
    var smallest = IntArray(k)
    var index = 0
    for (i in arr) {
        if (i <= threshold) {
            smallest[index++] = i
        }
    }
    return smallest
}

fun rank(arr: IntArray, rank: Int): Int {
    return rank(arr, 0, arr.size - 1, rank)
}

fun rank(arr: IntArray, left: Int, right: Int, currentRank: Int): Int {
    var pivot = arr[randomIntInRange(left, right)]
    var leftEnd = partition(arr, left, right, pivot)
    var leftSize = leftEnd - left + 1
    return if (currentRank == leftSize - 1) max(arr, left, leftEnd)
    else if (currentRank < leftSize) return rank(arr, left, leftEnd, currentRank)
    else rank(arr, leftEnd + 1, right, currentRank - leftSize)
}

fun partition(arr: IntArray, left: Int, right: Int, pivot: Int): Int {
    var start = left
    var end = right
    while (start <= end) {
        if (arr[start] > pivot) {
            swap(arr, start, end)
            end--
        } else if (arr[end] <= pivot) {
            swap(arr, start, end)
            start++
        } else {
            start++
            end--
        }
    }
    return start - 1
}

fun swap(arr: IntArray, left: Int, right: Int) {
    var temp = arr[left]
    arr[left] = arr[right]
    arr[right] = temp
}

fun max(arr: IntArray, start: Int, end: Int): Int {
    var max = Int.MIN_VALUE
    for (i in start..end) {
        max = maxOf(arr[i], max)
    }
    return max
}

fun randomIntInRange(min: Int, max: Int): Int {
    return Random().nextInt(max - min + 1) + min
}


*/
       */
