using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Net.Mime.MediaTypeNames;

namespace Lab7.Writers
{
    public class ConsoleWriter : IWriter
    {
        public void Write(string text)
        {
            int linesCount = text.Select(c => c).Where(c => c == '|').ToList().Count;
            if (!(text.Contains('[') && text.Contains(']') || linesCount != 0 && linesCount % 2 == 0))
            {
                Console.WriteLine(text);
                return;
            }

            char beginCharToCheck = text.Contains('[') ? '[' : '|';
            char endCharToCheck = text.Contains(']') ? ']' : '|';
            int beginIndex = text.IndexOf(beginCharToCheck);
            int endIndex = text.LastIndexOf(endCharToCheck);

            string part1 = text.Substring(0, beginIndex - 1);
            string part2 = text.Substring(beginIndex, endIndex - beginIndex + 1);
            string part3 = text.Substring(endIndex + 1);
            
            Console.Write(part1);
            if (text.Contains('['))
            {
                ColorFolderName(part2);
            }
            else
            {
                ColorFileName(part2);
            }
            Console.WriteLine(part3);
        }

        private void ColorFolderName(string text)
        {
            Console.BackgroundColor = ConsoleColor.Green;
            Console.ForegroundColor = ConsoleColor.Black;
            Console.Write(text);
            Console.ResetColor();
        }
        private void ColorFileName(string text) 
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write(text);
            Console.ResetColor();
        }
    }
}
