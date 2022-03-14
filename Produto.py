from math import prod
from forex_python.converter import CurrencyRates


product = input('Enter the product name you wanna buy: ')
print("Product chosen: {}".format(product))

productPriceUSD = float(input('Enter your {} price: '.format(product)))
print("{} price is: USD$ {}".format(product, productPriceUSD))


c = CurrencyRates()

priceBRL = c.get_rate('USD', 'BRL')

IOF = float(input('Enter the IOF tax of you credit card in %: '))
IOF = IOF/100
print("Current IOF value = {}".format(IOF))

priceBRL = priceBRL*(1+IOF)
print("USD$ 1.00 --------> BRL {}".format(priceBRL))


productWeight = float(input("Enter you {} weithgt in pounds: ".format(product)))
productWeight = int(productWeight) + 1
print("{} weight: {} pounds.".format(product, productWeight))




deliveryStandardPrice = 18.9*(productWeight)
print("DeliveryPrice = USD$ {}".format(deliveryStandardPrice))

finalPriceUSA = round((productPriceUSD + deliveryStandardPrice)*priceBRL , 2)

print()
print("The final price of your {} in the USA is R${}".format(product, finalPriceUSA))

brazilianFullTaxFinalPrice = (productPriceUSD + deliveryStandardPrice)*1.6

USD100declare = round(finalPriceUSA + 100*priceBRL*0.6, 2)
print()
print("If you declare your {} is worth USD $ 100.00, you'll pay: R${}".format(product, USD100declare))
total = round((brazilianFullTaxFinalPrice)*priceBRL, 2)
print()
print("In the worst case, with full taxes in BR, you'll spend R${} in your {}".format(total, product))



