package week1

object lecture1_7 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(106); 
	def gcd(a: Int, b: Int): Int = {
		if (b == 0) a else gcd(b, a % b)
	};System.out.println("""gcd: (a: Int, b: Int)Int""");$skip(13); val res$0 = 
	gcd(14, 21);System.out.println("""res0: Int = """ + $show(res$0));$skip(79); 
	
	def factorial(n: Int): Int = {
		if (n == 0) 1 else n * factorial(n - 1)
	};System.out.println("""factorial: (n: Int)Int""");$skip(14); val res$1 = 
	factorial(4);System.out.println("""res1: Int = """ + $show(res$1));$skip(25); val res$2 = 
	4 * (3 * (2 * (1 * 1)));System.out.println("""res2: Int = """ + $show(res$2))}
}
