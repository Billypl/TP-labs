using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab7.Writers
{
    public class StringLengthComparer : IComparer<string>
    {
        public int Compare(string? first, string? second)
        {
            if (first == null || second == null)
            {
                throw new ArgumentNullException();
            }
            if (first.Length == second.Length)
            {
                return string.Compare(first, second);
            }
            else
            {
                return first.Length > second.Length ? 1 : -1;
            }
        }
    }
}
