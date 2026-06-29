import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
    plugins: [react()],
    build: {
        outDir: 'assets',
        emptyOutDir: true,
        rollupOptions: {
            input: 'src/index.tsx',
            output: {
                entryFileNames: 'index.js',
                assetFileNames: (assetInfo) => {
                    if (assetInfo.name?.endsWith('.css')) return 'style.css';
                    return assetInfo.name ?? '[name][extname]';
                },
                format: 'iife',
                name: 'OddsparkImportantNotice',
                inlineDynamicImports: true,
            },
        },
    },
});
