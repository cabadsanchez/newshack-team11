import sys
import urllib
from bs4 import BeautifulSoup
import json
import pprint
import argparse

def fetchBBCArticle(articleURL, outPath):
	apiURL = "http://juicer-mk2.herokuapp.com/api/article?url=" + articleURL
	response = urllib.urlopen(apiURL)
	

	# verify we have received on OK response
	if response.getcode() == 200:
		data = json.loads(response.read())
		# print "Response code: ", response.getcode()
		print "\n Data: \n"
		pprint.pprint(data)

		with open(outPath, 'w') as outfile:
			json.dump(data, outfile)
			print "\n Written data to "+ outPath
	else: 
		print "WARNING: failed to get data"
		exit()


def main():
	parser = argparse.ArgumentParser(description='download article via BBC API')
	parser.add_argument('articleURL', 
		help='the URL of the article to scrape (URLparams removed)')
	parser.add_argument('outputPath',
		help='json path to write result to')
	args = parser.parse_args()
	print "fetching article at: ", args.articleURL

	# articleURL = "http://www.bbc.co.uk/news/world-australia-30473983"
	fetchBBCArticle(args.articleURL, args.outputPath)

if __name__ == '__main__':
	main()
