export function formatNumberToTwoDecimalPlaces(value: number): number {
  return parseFloat(value.toFixed(2));
}
