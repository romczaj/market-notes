export function getColorForValue(value: number): string {

  if (value <= -5) {
    value = -5
  }
  if (value >= 5) {
    value = 5
  }

  const red = {r: 255, g: 0, b: 0};
  const white = {r: 255, g: 255, b: 255};
  const green = {r: 0, g: 128, b: 0};

  let r, g, b;

  if (value <= 0) {
    // Calculate the ratio for values from -5 to 0 (red to white)
    const ratio = (value + 5) / 5;
    r = Math.ceil(red.r + ratio * (white.r - red.r));
    g = Math.ceil(red.g + ratio * (white.g - red.g));
    b = Math.ceil(red.b + ratio * (white.b - red.b));
  } else {
    // Calculate the ratio for values from 0 to 5 (white to green)
    const ratio = value / 5;
    r = Math.ceil(white.r + ratio * (green.r - white.r));
    g = Math.ceil(white.g + ratio * (green.g - white.g));
    b = Math.ceil(white.b + ratio * (green.b - white.b));
  }

  // Ensure the values are within 0-255
  r = Math.min(255, Math.max(0, r));
  g = Math.min(255, Math.max(0, g));
  b = Math.min(255, Math.max(0, b));

  // Return the color in hexadecimal format
  return `#${r.toString(16).padStart(2, '0')}${g.toString(16).padStart(2, '0')}${b.toString(16).padStart(2, '0')}`;
}
